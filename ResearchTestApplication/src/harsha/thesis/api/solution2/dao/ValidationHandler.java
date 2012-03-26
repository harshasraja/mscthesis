/**
 * 
 */
package harsha.thesis.api.solution2.dao;

import harsha.api.annotation.PrimaryKey;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution2.entity.BaseEntity;
import harsha.thesis.api.solution2.entity.Metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author harshasraja
 *
 */
public class ValidationHandler {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private BaseEntity entity = null;
    private String columnFamily = null;

    /**
     * @throws Exception
     *
     */
    public ValidationHandler(BaseEntity entity, BaseDAO dao) throws Exception {
        logger.debug("Invoked Validation Helper");
        this.entity = entity;
        this.columnFamily = entity.getColumnFamilyRepresentation();
        logger.debug("Invoked Validation Helper for Column family:" + this.columnFamily);
    }

    private ValidationHandler() {
    }

    public void checkReferenedKey() throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + this.columnFamily);
        List<BaseEntity> rConstraints = new LinkedList<BaseEntity>();
        BaseDAO dao = null;
        try {
            dao = new BaseDAO();
            
            List<Metadata> list = dao.read(entity.getColumnFamilyRepresentation().replace("_", "."), "-1").getMetaData();
            for (Metadata metadata : list) {
                if ("R".equals(metadata.getConstraintType())) {
                    rConstraints.add(metadata);
                }
            }
            for (BaseEntity rConstraint : rConstraints) {
                BaseEntity baseEntity = getMetadata((Metadata) rConstraint);

                Metadata metadata = (Metadata) baseEntity;
                if ("F".equals(metadata.getConstraintType())) {
                    String foreignKey = getForeignKey(metadata.getRColumn());

                    BaseEntity fkentity = dao.read(((Metadata) rConstraint).getTableName().replace("_", "."), foreignKey);

                    if (fkentity.isNull()) {
                        throw new ValidationFailedException(foreignKey + " not found in referenced table " + metadata.getTableName());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void checkForeignKey() throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + this.columnFamily);
        List<Metadata> rConstraints = new LinkedList<Metadata>();
        BaseDAO dao = null;
        try {
            dao = new BaseDAO();
            List<Metadata> list = dao.read(entity.getColumnFamilyRepresentation().replace("_", "."), "-1").getMetaData();
            for (Metadata metadata : list) {

                if ("F".equals(metadata.getConstraintType())) {
                    rConstraints.add(metadata);
                }
            }
            for (Metadata rConstraint : rConstraints) {
                BaseEntity baseEntity = getMetadata(rConstraint);

                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    //String primaryKey = getPrimaryKeyForEntity();
                    String primaryKey = getPrimaryKeyForEntity();
                    List<BaseEntity> childObjects = dao.read(rConstraint.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);
                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(rConstraint.getDeleteRule())) {
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(rConstraint.getDeleteRule())) {
                            dao.delete(childObject);
                        } else if (!childObject.isNull()) {
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + rConstraint.getDeleteRule() + " METADATA");
                        }
                    }

                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

    }

    public List<List<BaseEntity>> checkForeignKeyForUpdate() throws Exception {
        logger.debug("Checking ForeignKeyForUpdate for ColumnFamily:" + this.columnFamily);
        List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();
        List<Metadata> rConstraintNames = new LinkedList<Metadata>();
        BaseDAO dao = null;
        try {
            dao = new BaseDAO();
            List<Metadata> list = dao.read(entity.getColumnFamilyRepresentation().replace("_", "."), "-1").getMetaData();
            for (Metadata metadata : list) {

                if ("F".equals(metadata.getConstraintType())) {
                    rConstraintNames.add(metadata);
                }
            }
            for (Metadata rConstraintName : rConstraintNames) {
                BaseEntity baseEntity = getMetadata(rConstraintName);

                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    //String primaryKey = getPrimaryKeyForEntity();
                    String primaryKey = getPrimaryKeyForEntity();
                    List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);
                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE}".equalsIgnoreCase(metadata.getDeleteRule())) {
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE}".equalsIgnoreCase(metadata.getDeleteRule())) {
                            logger.debug("Update on cascade mode");
                            dao.delete(childObject);
                        } else if (!childObject.isNull()) {
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + metadata.getDeleteRule() + " METADATA");
                        }
                    }
                    childObjectList.add(childObjects);

                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

        return childObjectList;

    }

    public Map<String, String> getReferencedKeyFieldForForeignKey() throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        BaseDAO dao = new BaseDAO();
        List<Metadata> list = dao.read(entity.getColumnFamilyRepresentation().replace("_", "."), "-1").getMetaData();
        for (Metadata metadata : list) {

            if ("F".equals(metadata.getConstraintType())) {
                map.put(metadata.getTableName(), metadata.getRColumn());
            }
        }

        return map;
    }

    public void checkUniqueKey() throws Exception {
        logger.info("Checking Unique Key for ColumnFamily:" + this.columnFamily);
        List<String> constraintNames = new LinkedList<String>();
        BaseDAO dao = null;
        try {
            dao = new BaseDAO();
            List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
            for (BaseEntity baseEntity : list) {
                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("P".equals(metadata.getConstraintType())) {
                    constraintNames.add(metadata.getConstraintName());
                }
            }
            Method[] methods = entity.getClass().getDeclaredMethods();
            for (String constraintName : constraintNames) {
                for (Method method : methods) {
                    if (method.getName().equals("get" + getPrimaryKeyForEntity())) {
                        String primaryKey = (String) method.invoke(entity);
                        BaseEntity tempObject = dao.read(entity.getColumnFamilyRepresentation(), primaryKey);
                        if (tempObject != null) {
                            throw new ValidationFailedException("ID " + primaryKey + " is not unique in table " + entity.getColumnFamilyRepresentation());
                        }
                    }
                }

            }


        } catch (Exception e) {
            throw e;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

    }

    private String getPrimaryKeyForEntity() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String primaryKey = null;
        String primaryKeyField = null;
        Annotation[] a1 = this.entity.getClass().getDeclaredAnnotations();
        Method[] methods = entity.getClass().getDeclaredMethods();

        for (Annotation annotation : a1) {
            //System.out.println(annotation);
            if (annotation instanceof PrimaryKey) {
                primaryKeyField = ((PrimaryKey) annotation).primaryKey();
            }
        }

        for (Method method : methods) {
            if (method.getName().contains(primaryKeyField)
                    && method.getName().equals("get" + primaryKeyField)) {
                primaryKey = (String) method.invoke(entity);
                break;
            }
        }


        return primaryKey;
    }

    private String getForeignKey(String columnName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String foreignKey = null;
        //String primaryKeyField = null;
        //Annotation [] a1 = this.entity.getClass().getDeclaredAnnotations();
        Method[] methods = entity.getClass().getDeclaredMethods();

        //for (Annotation annotation : a1) {
        //System.out.println(annotation);
        //if (annotation instanceof PrimaryKey) {
        //primaryKeyField = ((PrimaryKey) annotation).primaryKey();
        //}
        //}

        for (Method method : methods) {
            if (method.getName().contains(columnName)
                    && method.getName().equals("get" + columnName)) {
                foreignKey = (String) method.invoke(entity);
                break;
            }
        }


        return foreignKey;
    }

    private Metadata getMetadata(Metadata rConstraint) throws Exception {
        BaseDAO dao = null;
        try {
            dao = new BaseDAO();
            List<Metadata> metaList = dao.read(rConstraint.getTableName().replace("_", "."), "-1").getMetaData();
            for (Metadata metadata : metaList) {
                if (rConstraint.getRConstraintName().equals(metadata.getConstraintName())) {
                    return metadata;
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            dao.close();
        }
        return null;
    }
}
