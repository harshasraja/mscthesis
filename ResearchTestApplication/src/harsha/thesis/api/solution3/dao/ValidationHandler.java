/**
*
*/
package harsha.thesis.api.solution3.dao;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.ConnectionDefinition;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution3.entity.BaseEntity;
import harsha.thesis.api.solution3.entity.Metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
* @author vinay
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

    public ValidationHandler(BaseEntity entity) throws Exception {
        logger.debug("Invoked Validation Helper");
        this.entity = entity;
        this.columnFamily = entity.getColumnFamilyRepresentation();
        logger.debug("Invoked Validation Helper for Column family:" + this.columnFamily);
    }

    
    private ValidationHandler() {
    }

    public void checkReferenedKey() throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + this.columnFamily);
        List<String> rConstraintNames = new LinkedList<String>();
        BaseDAO dao = new BaseDAO();
        try {
            List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
            for (BaseEntity baseEntity : list) {
                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    rConstraintNames.add(metadata.getRConstraintName());
                }
            }
            for (String rConstraintName : rConstraintNames) {
                BaseEntity baseEntity = dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName);
                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("P".equals(metadata.getConstraintType())) {
                    String foreignKey = getForeignKey(metadata.getRColumn());
                    BaseEntity temp = dao.read(metadata.getTableName().replace("_", "."), foreignKey);
                    if (temp.isNull()) {
                        throw new ValidationFailedException(foreignKey + " not found in referenced table " + metadata.getTableName());
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            logger.warn(e.getMessage(), e);
        } catch (InstantiationException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            dao.close();
        }
    }

    public void checkForeignKey() throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + this.columnFamily);
        List<Metadata> foreignKeys = new LinkedList<Metadata>();
        BaseDAO dao = new BaseDAO();
        try {
            List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
            for (BaseEntity baseEntity : list) {
                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("F".equals(metadata.getConstraintType())) {
                    foreignKeys.add(metadata);
                }
            }

            for (Metadata rConstraintName : foreignKeys) {
                BaseEntity baseEntity = dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());

                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    //String primaryKey = getPrimaryKeyForEntity();
                    String primaryKey = getPrimaryKeyForEntity().trim();
// dao = new BaseDAO(dao.getConnectionDefinition());
                    List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);
                    //List<BaseEntity> childObjects = dao.read("harsha.thesis.api.solution3.entity.Enrolment", "UserId", BaseDAO.EXPRESSION_EQUALS, "100",true);
// dao.close();
                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(rConstraintName.getDeleteRule())) {
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(rConstraintName.getDeleteRule())) {
// dao = new BaseDAO(dao.getConnectionDefinition());
                            dao.delete(childObject);
// dao.close();
                        } else if (!childObject.isNull()) {
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + metadata.getDeleteRule() + " METADATA");
                        }
                    }

                }
            }
        } catch (Exception e) {
//        	e.printStackTrace();
            logger.warn(e.getMessage(), e);
        } finally {
            dao.close();
        }

    }

    public List<List<BaseEntity>> checkForeignKeyForUpdate() throws Exception {
        logger.info("Checking ForeignKeyForUpdate for ColumnFamily:" + this.columnFamily);
        List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();
        List<Metadata> foreignKeys = new LinkedList<Metadata>();

        BaseDAO dao = new BaseDAO();
        try {
            List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);

            for (BaseEntity baseEntity : list) {

                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;

                if ("F".equals(metadata.getConstraintType())) {
                    foreignKeys.add(metadata);
                }
            }
            for (Metadata rConstraintName : foreignKeys) {
                BaseEntity baseEntity = dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());

                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    //String primaryKey = getPrimaryKeyForEntity();
                    String primaryKey = getPrimaryKeyForEntity();

                    List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);

                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())) {
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())) {
                            logger.debug("Update on cascade mode");
                            //dao.delete(childObject);
                        } else if (!childObject.isNull()) {
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + metadata.getDeleteRule() + " METADATA");
                        }
                    }
                    childObjectList.add(childObjects);

                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            dao.close();
        }

        return childObjectList;

    }

    public Map<String, String> getReferencedKeyFieldForForeignKey() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        Map<String, String> map = new HashMap<String, String>();
        BaseDAO dao = new BaseDAO();
        try {
            List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
            List<Metadata> foreignKeys = new LinkedList<Metadata>();

            for (BaseEntity baseEntity : list) {



                Metadata metadata = (Metadata) baseEntity;

                if ("F".equals(metadata.getConstraintType())) {
                    foreignKeys.add(metadata);
                }
            }

            for (Metadata rConstraintName : foreignKeys) {
                BaseEntity baseEntity = dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());

                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    map.put(metadata.getTableName(), metadata.getRColumn());

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dao.close();
        }
        return map;
    }

    public void checkUniqueKey() throws Exception {
        logger.info("Checking Unique Key for ColumnFamily:" + this.columnFamily);
        List<String> constraintNames = new LinkedList<String>();
        BaseDAO dao = new BaseDAO();
        try {
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
            logger.warn(e.getMessage(), e);
        } finally {
            dao.close();
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
                primaryKey = (String) method.invoke(entity, null);
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
}