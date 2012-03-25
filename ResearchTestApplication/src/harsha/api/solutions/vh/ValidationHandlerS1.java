/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS1 extends CommonValidationHandler {

    public ValidationHandlerS1(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution1";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return Constraint.Parse(entity.getMetadata());
    }

    public Constraint retrievePConstraint(Constraint rConstraint, Entity entity) {
        
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
                Constraint pConstraint = retrievePConstraint(constraint, entity);
                        //em.find(Constraint.class, constraint.getRConstraintName());

                String foreignKey = pConstraint.getRColumn();
                String foreignKeyValue = Entity.GetValue(foreignKey, entity);

                String primaryColumnFamily = pConstraint.getColumnFamily();
                Class<Entity> clazz = (Class<Entity>) Class.forName(primaryColumnFamily);

                Entity primaryKey = em.find(clazz, foreignKeyValue);
                if (primaryKey == null) {
                    String foreign = "Foreign: " + entity;
                    String primary = "Primary:" + clazz + " with value " + foreignKeyValue;
                    throw new Exception("Entity NOT FOUND: " + foreign + primary );
                }
            }
        }


    }

    @Override
    public void beforeUpdate(Entity entity) throws Exception {
        super.beforeUpdate(entity);
    }

    @Override
    public void afterUpdate(Entity entity) throws Exception {
        super.afterUpdate(entity);
    }

    @Override
    public void onDelete(Entity entity) throws Exception {
        super.onDelete(entity);
    }

    @Override
    public List<Entity> retrieveChildren(Entity entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
