/**
 * 
 */
package harsha.thesis.api.solution1.dao;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution1.entity.BaseEntity;
import harsha.thesis.api.solution1.entity.Metadata;

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
	private BaseDAO dao = null;
	
	/**
	 * 
	 */
	public ValidationHandler(BaseEntity entity, BaseDAO dao) {
		logger.debug("Invoked Validation Helper");
		this.entity = entity;
		this.columnFamily = entity.getColumnFamilyRepresentation();
		this.dao = dao;
		logger.debug("Invoked Validation Helper for Column family:"+this.columnFamily);
	}
	
	private ValidationHandler(){
		
	}
	
	public void checkReferenedKey() throws Exception{
		logger.info("Checking Referenced Key for ColumnFamily:"+this.columnFamily);
		List<String> rConstraintNames = new LinkedList<String>();
		try {
			List<Metadata> list = entity.getMetaData();
			for (Metadata metadata : list) {
				if ("R".equals(metadata.getConstraintType())) {
					rConstraintNames.add(metadata.getRConstraintName());
				}
			}
			for (String rConstraintName : rConstraintNames) {
				BaseEntity baseEntity =  getMetadata(rConstraintName);
				
				Metadata metadata = (Metadata) baseEntity;
				if("R".equals(metadata.getConstraintType())){
					String foreignKey = getForeignKey(metadata.getRColumn());
					
					BaseEntity fkentity = dao.read(metadata.getTableName().replace("_", "."), foreignKey);;
					
					if(fkentity.isNull()){
						throw new ValidationFailedException(foreignKey + " not found in referenced table "+metadata.getTableName());
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
	
	public void checkForeignKey() throws Exception{
		logger.info("Checking Referenced Key for ColumnFamily:"+this.columnFamily);
		List<BaseEntity> childObjects = null;
		List<String> rConstraintNames = new LinkedList<String>();
		try {
			List<Metadata> list = entity.getMetaData();
			for (Metadata metadata : list) {
				
				if ("F".equals(metadata.getConstraintType())) {
					rConstraintNames.add(metadata.getRConstraintName());
				}
			}
			for (String rConstraintName : rConstraintNames) {
				BaseEntity baseEntity =  getMetadata(rConstraintName);
				
				Metadata metadata = (Metadata) baseEntity;
				if("F".equals(metadata.getConstraintType())){
					//String primaryKey = getPrimaryKeyForEntity();
					String primaryKey = getPrimaryKeyForEntity();
					childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					for (BaseEntity childObject : childObjects) {
						if (!childObject.isNull() && "NODELETE}".equalsIgnoreCase(metadata.getDeleteRule())){
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE}".equalsIgnoreCase(metadata.getDeleteRule())){
							dao.delete(childObject);
						} else if (!childObject.isNull()){
							throw new Exception ("Fatal error! Failed understanding Delete Rule "+metadata.getDeleteRule()+" METADATA");
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
		
		//return child
		
	}
	
	public List<List<BaseEntity>> checkForeignKeyForUpdate() throws Exception{
		logger.info("Checking ForeignKeyForUpdate for ColumnFamily:"+this.columnFamily);
		List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();
		List<String> rConstraintNames = new LinkedList<String>();
		try {
			List<Metadata> list = entity.getMetaData();
			for (Metadata metadata : list) {
				
				if ("F".equals(metadata.getConstraintType())) {
					rConstraintNames.add(metadata.getRConstraintName());
				}
			}
			for (String rConstraintName : rConstraintNames) {
				BaseEntity baseEntity =  getMetadata(rConstraintName);
				
				Metadata metadata = (Metadata) baseEntity;
				if("F".equals(metadata.getConstraintType())){
					//String primaryKey = getPrimaryKeyForEntity();
					String primaryKey = getPrimaryKeyForEntity();
					List<BaseEntity> childObjects = dao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					for (BaseEntity childObject : childObjects) {
						if (!childObject.isNull() && "NODELETE}".equalsIgnoreCase(metadata.getDeleteRule())){
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE}".equalsIgnoreCase(metadata.getDeleteRule())){
							logger.debug("Update on cascade mode");
							//dao.delete(childObject);
						} else if (!childObject.isNull()){
							throw new Exception ("Fatal error! Failed understanding Delete Rule "+metadata.getDeleteRule()+" METADATA");
						}
					}
					childObjectList.add(childObjects);
					
				}
			}
		} catch (ClassNotFoundException e) {
			logger.warn(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.warn(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.warn(e.getMessage(), e);
		} 
		
		return childObjectList;
		
	}
	
	public Map<String, String> getReferencedKeyFieldForForeignKey(){
		Map<String, String> map = new HashMap<String, String>();
		List<Metadata> list = entity.getMetaData();
		for (Metadata metadata : list) {
			
			if ("F".equals(metadata.getConstraintType())) {
				map.put(metadata.getTableName(), metadata.getRColumn());
			}
		}
		
		return map;
	}
	
	public void checkUniqueKey() throws Exception{
		logger.info("Checking Unique Key for ColumnFamily:"+this.columnFamily);
		List<String> constraintNames = new LinkedList<String>();
		try {
			List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
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
					if (method.getName().equals("get"+getPrimaryKeyForEntity())) {
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
	
	private String getPrimaryKeyForEntity() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String primaryKey = null;
		String primaryKeyField = null;
		Annotation [] a1 = this.entity.getClass().getDeclaredAnnotations();
		Method [] methods = entity.getClass().getDeclaredMethods();
		
		for (Annotation annotation : a1) {
			System.out.println(annotation);
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
	
	private String getForeignKey(String columnName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String foreignKey = null;
		//String primaryKeyField = null;
		//Annotation [] a1 = this.entity.getClass().getDeclaredAnnotations();
		Method [] methods = entity.getClass().getDeclaredMethods();
		
		//for (Annotation annotation : a1) {
			//System.out.println(annotation);
			//if (annotation instanceof PrimaryKey) {
				//primaryKeyField = ((PrimaryKey) annotation).primaryKey();
			//}
		//}
		
		for (Method method : methods) {
			if (method.getName().contains(columnName) && 
					method.getName().equals("get"+columnName)) {
				foreignKey = (String) method.invoke(entity, null);
				break;
			}
		}
		
		
		return foreignKey;
	}
	
	
	private Metadata getMetadata(String rConstraintName){
		List<Metadata> metaList = entity.getMetaData();
		for (Metadata metadata : metaList) {
			if (rConstraintName.equals(metadata.getRConstraintName())){
				return metadata;
			}
		}
		return null;
	}

}
