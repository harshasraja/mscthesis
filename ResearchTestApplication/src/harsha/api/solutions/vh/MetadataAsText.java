/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import harsha.api.ValidationHandler;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author harshasraja
 */
public abstract class MetadataAsText implements ValidationHandler {

    protected static final Logger LOG = Logger.getLogger(MetadataAsText.class);
    protected EntityManager em;

    public MetadataAsText(EntityManager em) {
        this.em = em;
    }

    public Constraint findConstraint(String constraintName, List<Constraint> metadata) {
        for (Constraint constraint : metadata) {
            if (constraint.getConstraintName().equals(constraintName)) {
                return constraint;
            }
        }
        return null;
    }

//    @Override
//    public List<Entity> retrieveChildren(Entity entity) throws Exception {
//        List<Entity> result = new ArrayList<Entity>();
//        List<Constraint> metadata = retrieveMetadata(entity);
//        for (Constraint fConstraint : metadata) {
//            if ("F".equals(fConstraint.getConstraintType())) {
//                Constraint rConstraint = em.find(Constraint.class, fConstraint.getRConstraintName());
//                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
//                String foreignColumn = rConstraint.getRColumn(); //
//
//                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);
//
//                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);
//
//                List<Entity> children = em.query(clazz,
//                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);
//                result.addAll(children);
//            }
//        }
//        return result;
//    }
    @Override
    public void onInsert(Entity entity) throws Exception {
        //retrieve Metadata has to find all constraints where columnfamily  = entity;
        LOG.debug("onInsert(" + entity + ")");
        List<Constraint> metadata = retrieveMetadata(entity);

        for (Constraint constraint : metadata) {
            if (constraint.getColumnFamily().equals(entity.getClass().getName())
                    && "R".equals(constraint.getConstraintType())) {
                LOG.debug(constraint);
                Constraint rConstraint = findConstraint(constraint.getRConstraintName(),
                        metadata);
                LOG.debug(rConstraint);
                String foreignKey = rConstraint.getRColumn();
                String foreignKeyValue = Entity.GetValue(foreignKey, entity);

                String primaryColumnFamily = rConstraint.getColumnFamily();
                Class<Entity> clazz = (Class<Entity>) Class.forName(primaryColumnFamily);

                Entity primaryKey = em.find(clazz, foreignKeyValue);
                if (primaryKey == null) {
                    throw new Exception("Entity NOT found: " + clazz + "[" +  foreignKey + "=" + foreignKeyValue + "]");
                }
            }
        }
    }

    @Override
    public void onUpdate(Entity entity) throws Exception {
        //Entity is OLD Student. Assumed that new Student is already inserted.
        List<Constraint> metadata = retrieveMetadata(entity);
        for (Constraint fConstraint : metadata) {
            if (fConstraint.getColumnFamily().equals(entity.getClass().getName())
                    && "F".equals(fConstraint.getConstraintType()))  {
                Constraint rConstraint = findConstraint(fConstraint.getRConstraintName(), metadata);

                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
                String foreignColumn = rConstraint.getRColumn(); //

                //Here I am getting the old value for StudentId in Enrolment
                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);

                //Here I get the Enrolment.class
                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);

                List<Entity> children = em.query(clazz,
                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);

                if (!children.isEmpty()) {
                    if ("CASCADE".equals(fConstraint.getDeleteRule())) {
                        for (Entity child : children) {
                            Entity.SetValue(foreignColumn, entity.getKeyForUpdate(), child);
                            em.update(child);
                        }
                    } else if ("NODELETE".equals(fConstraint.getDeleteRule())) {
                        throw new Exception("Entity has child dependencies and its PK cannot be updated: " + entity);
                    }
                    Entity.GetValue(foreignColumn, entity);
                }
            }
        }
    }
//

    @Override
    public void onDelete(Entity entity) throws Exception {

        List<Constraint> metadata = retrieveMetadata(entity);

        for (Constraint fConstraint : metadata) {
            if (fConstraint.getColumnFamily().equals(entity.getClass().getName()) &&
                    "F".equals(fConstraint.getConstraintType())) {
                Constraint rConstraint = findConstraint(fConstraint.getRConstraintName(), metadata);
                String foreignColumnFamily = rConstraint.getColumnFamily(); //child is Enrolment
                String foreignColumn = rConstraint.getRColumn(); //

                String primaryKeyValue = Entity.GetValue(foreignColumn, entity);

                Class<Entity> clazz = (Class<Entity>) Class.forName(foreignColumnFamily);

                List<Entity> children = em.query(clazz,
                        foreignColumn, EntityManager.Expression.EQUALS, primaryKeyValue);

                if (!children.isEmpty()) {
                    if ("CASCADE".equals(fConstraint.getDeleteRule())) {
                        for (Entity child : children) {
                            em.delete(child);
                        }
                    } else {
                        if ("NODELETE".equals(fConstraint.getDeleteRule())) {
                            throw new Exception(entity + " has child dependencies and cannot be deleted");
                        }
                    }
                }
            }
        }
    }
}
