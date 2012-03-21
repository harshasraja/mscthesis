/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.em.EntityManager;
import harsha.thesis.api.solution.entity.Entity;
import java.util.ArrayList;
import java.util.List;

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
//        List<Constraint> metadata = retrieveMetadata(entity);
//        List<Constraint> rConstraints = new ArrayList<Constraint>();
//        
//        for (Constraint constraint : metadata) {
//            if ("R".equals(constraint.getType())) {
//                rConstraints.add(constraint);
//            }
//        }
//
//        List<Entity> rConstraints = new ArrayList<Entity>();
//
//        try {
//            List<Constraint> list = em.read(entity.getClass()
//                    entity.getColumnFamilyRepresentation().replace("_", "."), "-1").getMetaData();
//            for (Metadata metadata : list) {
//                if ("R".equals(metadata.getConstraintType())) {
//                    rConstraints.add(metadata);
//                }
//            }
//            for (BaseEntity rConstraint : rConstraints) {
//                BaseEntity baseEntity = getMetadata((Metadata) rConstraint);
//
//                Metadata metadata = (Metadata) baseEntity;
//                if ("F".equals(metadata.getConstraintType())) {
//                    String foreignKey = getForeignKey(metadata.getRColumn());
//
//                    BaseEntity fkentity = dao.read(((Metadata) rConstraint).getTableName().replace("_", "."), foreignKey);
//
//                    if (fkentity.isNull()) {
//                        throw new ValidationFailedException(foreignKey + " not found in referenced table " + metadata.getTableName());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (dao != null) {
//                dao.close();
//            }
//        }
    }

    @Override
    public void onUpdate(Entity entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDelete(Entity entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Entity> retrieveDependencies(Entity entity) throws Exception {
        List<Entity> dependencies = new ArrayList<Entity>();
        
        return dependencies;
    }
}
