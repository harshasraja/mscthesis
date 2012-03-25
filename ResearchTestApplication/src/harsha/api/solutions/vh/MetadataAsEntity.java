/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import harsha.api.ValidationHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcrada
 */
public abstract class MetadataAsEntity implements ValidationHandler {

    protected EntityManager em;

    public MetadataAsEntity(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return em.query(Constraint.class, "ColumnFamily",
                EntityManager.Expression.EQUALS, entity.getClass().getName());
    }

    @Override
    public List<Entity> retrieveChildren(Entity entity) throws Exception {
        List<Entity> result = new ArrayList<Entity>();
        List<Constraint> metadata = retrieveMetadata(entity);
        for (Constraint fConstraint : metadata) {
            if ("F".equals(fConstraint.getConstraintType())) {
                Constraint rConstraint = em.find(Constraint.class, fConstraint.getRConstraintName());
                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
                String foreignColumn = rConstraint.getRColumn(); //

                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);

                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);

                List<Entity> children = em.query(clazz,
                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);
                result.addAll(children);
            }
        }
        return result;
    }
    
    @Override
    public void onInsert(Entity entity) throws Exception {

        Entity exists = em.find(entity.getClass(), Entity.GetValue(
                Entity.GetPrimaryKey(entity.getClass()), entity));

        if (exists != null) {
            throw new Exception("Cannot insert " + entity + " as it already exists");
        }

        //retrieve Metadata has to find all constraints where columnfamily  = entity;
        List<Constraint> metadata = retrieveMetadata(entity);

        for (Constraint constraint : metadata) {
            if ("R".equals(constraint.getConstraintType())) {
                Constraint rConstraint = em.find(Constraint.class, constraint.getRConstraintName());

                String foreignKey = rConstraint.getRColumn();
                String foreignKeyValue = Entity.GetValue(foreignKey, entity);

                String primaryColumnFamily = rConstraint.getColumnFamily();
                Class<Entity> clazz = (Class<Entity>) Class.forName(primaryColumnFamily);

                Entity primaryKey = em.find(clazz, foreignKeyValue);
                if (primaryKey == null) {
                    String foreign = "Foreign: " + entity;
                    String primary = "Primary:" + clazz + " with value " + foreignKeyValue;
                    throw new Exception(foreign + primary + " NOT FOUND :P");
                }
            }
        }
    }

    @Override
    public void beforeUpdate(Entity entity) throws Exception {
        List<Constraint> metadata = retrieveMetadata(entity);
        for (Constraint constraint : metadata) {
            if ("NODELETE".equals(constraint.getDeleteRule())) {
                throw new Exception("Primary key of " + entity + " cannot be updated");
            }
        }
    }

    public void afterUpdate(Entity entity) throws Exception {
//        List<Entity> children = this.validationHandler.retrieveChildren(entity);
//
//        //update dependencies to new foreign id.
//        for (Entity child : children) {
//
//            Entity.SetValue(Entity.GetName(entity.getClass()) + "Id",
//                    newId, child);
//
//            update(child);
//        }
        
        
        List<Constraint> metadata = retrieveMetadata(entity);

        for (Constraint fConstraint : metadata) {
            if ("F".equals(fConstraint.getConstraintType())) {
                Constraint rConstraint = em.find(Constraint.class, fConstraint.getRConstraintName());
                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
                String foreignColumn = rConstraint.getRColumn(); //

                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);

                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);

                List<Entity> children = em.query(clazz,
                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);

                if ("CASCADE".equals(fConstraint.getDeleteRule())) {
                    for (Entity child : children) {
                        
                        //TODO: update entity
                        
                    }
                } else {
                    if ("NODELETE".equals(fConstraint.getDeleteRule()) && !children.isEmpty()) {
                        throw new Exception(entity + " has child dependencies and cannot be deleted");
                    }
                }
            }
        }
    }

    @Override
    public void onDelete(Entity entity) throws Exception {

        List<Constraint> metadata = retrieveMetadata(entity);

        for (Constraint fConstraint : metadata) {
            if ("F".equals(fConstraint.getConstraintType())) {
                Constraint rConstraint = em.find(Constraint.class, fConstraint.getRConstraintName());
                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
                String foreignColumn = rConstraint.getRColumn(); //

                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);

                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);

                List<Entity> children = em.query(clazz,
                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);

                if ("CASCADE".equals(fConstraint.getDeleteRule())) {
                    for (Entity child : children) {
                        em.delete(child);
                    }
                } else {
                    if ("NODELETE".equals(fConstraint.getDeleteRule()) && !children.isEmpty()) {
                        throw new Exception(entity + " has child dependencies and cannot be deleted");
                    }
                }
            }
        }
    }

    
}
