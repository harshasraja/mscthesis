/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.ValidationHandler;
import harsha.api.EntityManager;
import harsha.api.Entity;
import java.util.ArrayList;
import java.util.List;
import org.apache.cassandra.utils.Pair;

/**
 *
 * @author jcrada
 */
public abstract class CommonValidationHandler implements ValidationHandler {

    protected EntityManager em;

    public CommonValidationHandler(EntityManager em) {
        this.em = em;
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
        List<Constraint> rConstraintNames =
                new ArrayList<Constraint>();

        for (Constraint constraint : metadata) {
            if ("R".equals(constraint.getConstraintType())) {
                rConstraintNames.add(em.find(Constraint.class, constraint.getRConstraintName()));
            }
        }

        for (Constraint rConstraint : rConstraintNames) {
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
                } else if ("NODELETE".equals(fConstraint.getDeleteRule()) && !children.isEmpty()) {
                    throw new Exception(entity + " has child dependencies and cannot be deleted");
                }
            }
        }
    }
}
