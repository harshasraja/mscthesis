/**
 * 
 */
package harsha.thesis.api.solution4.dao;

import harsha.api.annotation.PrimaryKey;
import harsha.api.connection.CloudConnector;
import harsha.api.connection.ConnectionDefinition;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution4.entity.BaseEntity;
import harsha.thesis.api.solution4.entity.Metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
    private String entityForValidation = null;
    private List<Metadata> allAssociatedMetadata = null;

    private ValidationHandler() {
    }


    public ValidationHandler(String entityType) throws Exception {
        this.entityForValidation = entityType.replace('.', '_');
        init();
    }

    public void setEntityForValidation(String entityType) {
        this.entityForValidation = entityType.replace('.', '_');
    }

    public void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        logger.debug("Invoked Validation Helper -> init");
        MetadataDAO metaDao = null;
        try {
            metaDao = new MetadataDAO();
            allAssociatedMetadata = new ArrayList<Metadata>();
            List<Metadata> tempList = new ArrayList<Metadata>();
            if (!Metadata.class.getName().equals(entityForValidation)) {
                List<BaseEntity> list = metaDao.read(Metadata.class.getName(), "TableName",
                        BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
                for (BaseEntity baseEntity : list) {
                    if (!(baseEntity instanceof Metadata)) {
                        throw new Exception("Fatal error! Failed loading METADATA");
                    }
                    Metadata metadata = (Metadata) baseEntity;
                    if ("R".equals(metadata.getConstraintType())) {
                        allAssociatedMetadata.add(metadata);
                        tempList.add(metadata);
                    }

                    if ("F".equals(metadata.getConstraintType())) {
                        allAssociatedMetadata.add(metadata);
                        tempList.add(metadata);
                    }
                }

                for (Metadata metadata : tempList) {
                    String constraintType = null;
                    if ("R".equals(metadata.getConstraintType())) {
                        constraintType = "P";

                    } else if ("F".equals(metadata.getConstraintType())) {
                        constraintType = "R";
                    }
                    Metadata temp = (Metadata) metaDao.read(Metadata.class.getName(), 
                            metadata.getRConstraintName());
                    if (constraintType.equals(temp.getConstraintType())) {
                        allAssociatedMetadata.add(temp);
                    }
                }


            }
            logger.debug("Invoked Validation Helper -> init. Success");
        } catch (Exception ec) {
        } finally {
            if (metaDao != null) {
                metaDao.close();
            }
        }


    }

    public void checkReferenedKey(BaseEntity entity) throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + entityForValidation);

        BaseDAO dao = null;
        try {
            dao = new BaseDAO(this);
            for (Metadata metadata : allAssociatedMetadata) {
                if ("P".equals(metadata.getConstraintType())) {
                    String foreignKey = getForeignKey(metadata.getRColumn(), entity);
                    BaseEntity temp = dao.read(metadata.getTableName().replace("_", "."), foreignKey);
                    if (temp.isNull()) {
                        //mDao.close();
                        throw new ValidationFailedException(foreignKey + " not found in referenced table " + metadata.getTableName());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

    }

    public void checkForeignKey(BaseEntity entity) throws Exception {
        logger.debug("Checking Referenced Key for ColumnFamily:" + this.entityForValidation);
        ValidationHandler handler = null;
        BaseDAO dao = null;
        try {
            dao = new BaseDAO(this);
            for (Metadata metadata : allAssociatedMetadata) {
                if ("F".equals(metadata.getConstraintType())) {
                    String primaryKey = getPrimaryKeyForEntity(entity).trim(); //6
                    List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);
                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())) {
                            //Dao.close();
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())) {
//                            handler = new ValidationHandler(this.entityForValidation);
//                            tempDao = new BaseDAO(this.dbConnectionDef, handler);
                            
                            dao.delete(childObject);
//                            tempDao.close();
                        } else if (!childObject.isNull()) {
                            //mDao.close();
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + metadata.getDeleteRule() + " METADATA");
                        }
                    }

                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }



    }

    public List<List<BaseEntity>> checkForeignKeyForUpdate(BaseEntity entity) throws Exception {
        logger.debug("Checking ForeignKeyForUpdate for ColumnFamily:" + entityForValidation);
        List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();

        BaseDAO dao = null;
        try {
            dao = new BaseDAO(this);
            for (Metadata metadata : allAssociatedMetadata) {
                if ("R".equals(metadata.getConstraintType())) {
                    String primaryKey = getPrimaryKeyForEntity(entity).trim();
                    List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey, true);
                    for (BaseEntity childObject : childObjects) {
                        if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())) {
                            //Dao.close();
                            throw new ValidationFailedException(primaryKey + " found in child table " + metadata.getTableName());
                        } else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())) {
                        } else if (!childObject.isNull()) {
                            //mDao.close();
                            throw new Exception("Fatal error! Failed understanding Delete Rule " + metadata.getDeleteRule() + " METADATA");
                        }
                    }
                    childObjectList.add(childObjects);
                }

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

        return childObjectList;

    }

    public Map<String, String> getReferencedKeyFieldForForeignKey() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        Map<String, String> map = new HashMap<String, String>();

        MetadataDAO metaDao = null;
        try {
            metaDao = new MetadataDAO();
            List<BaseEntity> list = metaDao.read(Metadata.class.getName(), "TableName", BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
            List<Metadata> foreignKeys = new LinkedList<Metadata>();

            for (BaseEntity baseEntity : list) {

                Metadata metadata = (Metadata) baseEntity;

                if ("F".equals(metadata.getConstraintType())) {
                    foreignKeys.add(metadata);
                }
            }

            for (Metadata rConstraintName : foreignKeys) {
                BaseEntity baseEntity = metaDao.read(Metadata.class.getName(), rConstraintName.getRConstraintName());

                Metadata metadata = (Metadata) baseEntity;
                if ("R".equals(metadata.getConstraintType())) {
                    map.put(metadata.getTableName(), metadata.getRColumn());

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (metaDao != null) {
                metaDao.close();
            }
        }
        return map;
    }

    public void checkUniqueKey(BaseEntity entity) throws Exception {
        logger.info("Checking Unique Key for ColumnFamily:" + entityForValidation);
        List<String> constraintNames = new LinkedList<String>();
        MetadataDAO metaDao = null;
        BaseDAO dao = null;
        try {
            metaDao = new MetadataDAO();
            dao = new BaseDAO(this);
            List<BaseEntity> list = metaDao.read(Metadata.class.getName(), "TableName", 
                    BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
            for (BaseEntity baseEntity : list) {
                if (!(baseEntity instanceof Metadata)) {
                    throw new Exception("Fatal error! Failed loading METADATA");
                }
                Metadata metadata = (Metadata) baseEntity;
                if ("P".equals(metadata.getConstraintType())) {
                    constraintNames.add(metadata.getConstraintName());
                }
            }
            //TODO: WHAT?! What's up with not using constraint name?
            Method[] methods = entity.getClass().getDeclaredMethods();
            for (String constraintName : constraintNames) {
                for (Method method : methods) {
                    if (method.getName().equals("get" + getPrimaryKeyForEntity(entity))) {
                        String primaryKey = (String) method.invoke(entity);
                        BaseEntity tempObject = dao.read(entity.getColumnFamilyRepresentation(), primaryKey);
                        if (tempObject != null) {
                            throw new ValidationFailedException("ID " + primaryKey + 
                                    " is not unique in table " + entity.getColumnFamilyRepresentation());
                        }
                    }
                }

            }


        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            ex.printStackTrace();
        } finally {
            if (dao != null) dao.close();
            if (metaDao != null) metaDao.close();
        }

    }

    private String getPrimaryKeyForEntity(BaseEntity entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String primaryKey = null;
        String primaryKeyField = null;
        Annotation[] a1 = entity.getClass().getDeclaredAnnotations();
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

    private String getForeignKey(String columnName, BaseEntity entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String foreignKey = null;

        Method[] methods = entity.getClass().getDeclaredMethods();

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
