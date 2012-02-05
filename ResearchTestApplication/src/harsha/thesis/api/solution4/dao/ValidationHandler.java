/**
 * 
 */
package harsha.thesis.api.solution4.dao;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.ConnectionDefinition;
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

	private BaseDAO dao = null;
	private MetadataDAO mDao = null;
	private String entityForValidation = null;
	private List<Metadata> allAssociatedMetadata = null;
	private ConnectionDefinition dbConnectionDef = null;
	private ConnectionDefinition metadataConDef = null;
	

	
	private ValidationHandler(){
		
	}
	
	public ValidationHandler(ConnectionDefinition metadataConDef) throws Exception{
		logger.debug("Invoked Validation Helper");
		this.mDao = new MetadataDAO(metadataConDef);
		this.metadataConDef = metadataConDef;
	}
	
	public ValidationHandler(ConnectionDefinition metadataConDef, ConnectionDefinition dbConDef, String entityType) throws Exception{
		this.mDao = new MetadataDAO(metadataConDef);
		this.entityForValidation = entityType.replace('.', '_');
		this.metadataConDef = metadataConDef;
		this.dbConnectionDef = dbConDef;
		init(dbConDef);
	}
	
	public void setEntityForValidation(String entityType){
		this.entityForValidation = entityType.replace('.', '_');
	}
	
	public void init(ConnectionDefinition dbConDef) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		
		logger.debug("Invoked Validation Helper -> init");
		this.dbConnectionDef = dbConDef;
		allAssociatedMetadata = new ArrayList<Metadata>();
		List<Metadata> tempList = new ArrayList<Metadata>();
		if (!"harsha.thesis.api.solution4.entity.Metadata".equals(entityForValidation)) {
			List<BaseEntity> list = mDao.read("harsha.thesis.api.solution4.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
			for (BaseEntity baseEntity : list) {
				if (!(baseEntity instanceof Metadata)) {
					mDao.close();
					throw new Exception ("Fatal error! Failed loading METADATA");
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
				if ("R".equals(metadata.getConstraintType())){
					constraintType = "P";
					
				} else if ("F".equals(metadata.getConstraintType())){
					constraintType = "R";	
				}
				Metadata temp = (Metadata)mDao.read("harsha.thesis.api.solution4.entity.Metadata", metadata.getRConstraintName());
				if (constraintType.equals(temp.getConstraintType())) {
					allAssociatedMetadata.add(temp);
				}
			}

	
		}

				logger.debug("Invoked Validation Helper -> init. Success");
		
	}
	
	public void closeHandler(){
		if (mDao != null){
			mDao.close();
		}
		if (dao != null){
			dao.close();
		}
	}
	
	public void checkReferenedKey(BaseEntity entity) throws Exception{
		logger.info("Checking Referenced Key for ColumnFamily:"+entityForValidation);
		try {
			this.dao = new BaseDAO(dbConnectionDef,this);
			for (Metadata metadata : allAssociatedMetadata) {
				if("P".equals(metadata.getConstraintType())){
					String foreignKey = getForeignKey(metadata.getRColumn(), entity);
					BaseEntity temp= dao.read(metadata.getTableName().replace("_", "."),foreignKey);
					if (temp.isNull()){
						//mDao.close();
						throw new ValidationFailedException(foreignKey + " not found in referenced table "+metadata.getTableName());
					}
				}	
			}
                        this.dao.close();
		} finally {
			
		}
				
	}
	
	public void checkForeignKey(BaseEntity entity) throws Exception{
		logger.info("Checking Referenced Key for ColumnFamily:"+this.entityForValidation);
		ValidationHandler handler = null;
		BaseDAO tempDao = null;
		try {
			this.dao = new BaseDAO(dbConnectionDef,this);
			for (Metadata metadata : allAssociatedMetadata) {
				if("R".equals(metadata.getConstraintType())){
					String primaryKey = getPrimaryKeyForEntity(entity).trim();
					List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					for (BaseEntity childObject : childObjects) {
						if ( !childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())){
							//Dao.close();
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())){
							handler = new ValidationHandler(this.metadataConDef,this.dbConnectionDef,this.entityForValidation);
							tempDao = new BaseDAO(this.dbConnectionDef, handler);
							tempDao.delete(childObject);
                                                        tempDao.close();
						} else if (!childObject.isNull()){
							//mDao.close();
							throw new Exception ("Fatal error! Failed understanding Delete Rule "+metadata.getDeleteRule()+" METADATA");
						}
					}
					
				}
			}
                        this.dao.close();
		} finally {
			
			
		}
		
		
	}
	
	public List<List<BaseEntity>> checkForeignKeyForUpdate(BaseEntity entity) throws Exception{
		logger.info("Checking ForeignKeyForUpdate for ColumnFamily:"+entityForValidation);
		List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();

		
		try {
			this.dao = new BaseDAO(dbConnectionDef,this);
			for (Metadata metadata : allAssociatedMetadata) {
				if("R".equals(metadata.getConstraintType())){
					String primaryKey = getPrimaryKeyForEntity(entity).trim();
					List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					for (BaseEntity childObject : childObjects) {
						if ( !childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())){
							//Dao.close();
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())){
						} else if (!childObject.isNull()){
							//mDao.close();
							throw new Exception ("Fatal error! Failed understanding Delete Rule "+metadata.getDeleteRule()+" METADATA");
						}
					}
					childObjectList.add(childObjects);
				}
				
			}

		} finally {
			
			this.dao.close();

		}
		
		return childObjectList;
		
	}
	
	public Map<String, String> getReferencedKeyFieldForForeignKey() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		Map<String, String> map = new HashMap<String, String>();
		List<BaseEntity> list = mDao.read("harsha.thesis.api.solution4.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
		List<Metadata> foreignKeys = new LinkedList<Metadata>();
		
		for (BaseEntity baseEntity : list) {
			

			
			Metadata metadata = (Metadata) baseEntity;
			
			if ("F".equals(metadata.getConstraintType())) {
				foreignKeys.add(metadata);
			}
		}
		
		for (Metadata rConstraintName : foreignKeys) {
			BaseEntity baseEntity =  mDao.read("harsha.thesis.api.solution4.entity.Metadata", rConstraintName.getRConstraintName());
			
			Metadata metadata = (Metadata) baseEntity;
			if("R".equals(metadata.getConstraintType())){
				map.put(metadata.getTableName(), metadata.getRColumn());

			}
		}
		//mDao.close();
		return map;
	}
	
	public void checkUniqueKey(BaseEntity entity) throws Exception{
		logger.info("Checking Unique Key for ColumnFamily:"+entityForValidation);
		List<String> constraintNames = new LinkedList<String>();
		try {
			List<BaseEntity> list = mDao.read("harsha.thesis.api.solution4.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, entityForValidation, true);
			for (BaseEntity baseEntity : list) {
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;
				if ("P".equals(metadata.getConstraintType())) {
					constraintNames.add(metadata.getConstraintName());
				}
			}
			Method[] methods = entity.getClass().getDeclaredMethods();
			for (String constraintName : constraintNames) {
				for (Method method : methods) {
					if (method.getName().equals("get"+getPrimaryKeyForEntity(entity))) {
						String primaryKey = (String)method.invoke(entity);
							BaseEntity tempObject = dao.read(entity.getColumnFamilyRepresentation(), primaryKey);
							if (tempObject != null){
								throw new ValidationFailedException("ID "+primaryKey + " is not unique in table "+entity.getColumnFamilyRepresentation());
							}
					}
				}
					
			}
				
			
		} catch (ClassNotFoundException e) {
			logger.warn(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.warn(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.warn(e.getMessage(), e);
		} 
		
	}
	
	private String getPrimaryKeyForEntity(BaseEntity entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String primaryKey = null;
		String primaryKeyField = null;
		Annotation [] a1 = entity.getClass().getDeclaredAnnotations();
		Method [] methods = entity.getClass().getDeclaredMethods();
		
		for (Annotation annotation : a1) {
			//System.out.println(annotation);
			if (annotation instanceof PrimaryKey) {
				primaryKeyField = ((PrimaryKey) annotation).primaryKey();
			}
		}
		
		for (Method method : methods) {
			if (method.getName().contains(primaryKeyField) && 
					method.getName().equals("get"+primaryKeyField)) {
				primaryKey = (String) method.invoke(entity, null);
				break;
			}
		}
		
		
		return primaryKey;
	}
	
	private String getForeignKey(String columnName, BaseEntity entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String foreignKey = null;

		Method [] methods = entity.getClass().getDeclaredMethods();
		
		for (Method method : methods) {
			if (method.getName().contains(columnName) && 
					method.getName().equals("get"+columnName)) {
				foreignKey = (String) method.invoke(entity);
				break;
			}
		}
		
		
		return foreignKey;
	}

}
