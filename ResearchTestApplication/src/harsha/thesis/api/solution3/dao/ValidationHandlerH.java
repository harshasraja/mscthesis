/**
 * 
 */
package harsha.thesis.api.solution3.dao;

import harsha.api.annotation.PrimaryKey;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution3.dao.BaseDAO;
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
 * @author harshasraja
 *
 */
public class ValidationHandlerH {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private BaseEntity entity = null;
	private String columnFamily = null;
	private BaseDAO dao = null;
	
	/**
	 * @throws Exception 
	 * 
	 */
	public ValidationHandlerH(BaseEntity entity, BaseDAO dao) throws Exception {
		logger.debug("Invoked Validation Helper");
		this.entity = entity;
		this.columnFamily = entity.getColumnFamilyRepresentation();
		this.dao = new BaseDAO();
		logger.debug("Invoked Validation Helper for Column family:"+this.columnFamily);
	}
	
	private ValidationHandlerH(){
		
	}
	
	public void checkReferenedKey() throws Exception{
		logger.info("Checking Referenced Key for ColumnFamily:"+this.columnFamily);
		List<String> rConstraintNames = new LinkedList<String>();
		try {
			List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
			for (BaseEntity baseEntity : list) {
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;
				if ("R".equals(metadata.getConstraintType())) {
					rConstraintNames.add(metadata.getRConstraintName());
				}
			}
			for (String rConstraintName : rConstraintNames) {
				BaseEntity baseEntity =  dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName);
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;	
				if("P".equals(metadata.getConstraintType())){
					String foreignKey = getForeignKey(metadata.getRColumn());
					BaseEntity temp= dao.read(metadata.getTableName().replace("_", "."),foreignKey);
					if (temp.isNull()){
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
		List<Metadata> foreignKeys = new LinkedList<Metadata>();
		try {
			BaseDAO newdao = new BaseDAO();
			List<BaseEntity> list = newdao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
			newdao.close();
			for (BaseEntity baseEntity : list) {
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;
				if ("F".equals(metadata.getConstraintType())) {
					foreignKeys.add(metadata);
				}
			}
			for (Metadata rConstraintName : foreignKeys) {
				newdao = new BaseDAO();
				BaseEntity baseEntity =  newdao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());
				newdao.close();
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;
				if("R".equals(metadata.getConstraintType())){
					//String primaryKey = getPrimaryKeyForEntity();
					String primaryKey = getPrimaryKeyForEntity().trim();
					newdao = new BaseDAO();
					List<BaseEntity> childObjects = newdao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					//List<BaseEntity> childObjects = dao.read("harsha.thesis.api.solution3.entity.Enrolment", "UserId", BaseDAO.EXPRESSION_EQUALS, "100",true);
					newdao.close();
					for (BaseEntity childObject : childObjects) {
						if ( !childObject.isNull() && "NODELETE".equalsIgnoreCase(rConstraintName.getDeleteRule())){
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(rConstraintName.getDeleteRule())){
							newdao = new BaseDAO();
							newdao.delete(childObject);
							newdao.close();
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
		
	}
	
	public List<List<BaseEntity>> checkForeignKeyForUpdate() throws Exception{
		logger.info("Checking ForeignKeyForUpdate for ColumnFamily:"+this.columnFamily);
		List<List<BaseEntity>> childObjectList = new LinkedList<List<BaseEntity>>();
		List<Metadata> foreignKeys = new LinkedList<Metadata>();
		
		try {
			List<BaseEntity> list = dao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
			
			for (BaseEntity baseEntity : list) {
				
				if (!(baseEntity instanceof Metadata)) {
					throw new Exception ("Fatal error! Failed loading METADATA");
				}
				Metadata metadata = (Metadata) baseEntity;
				
				if ("F".equals(metadata.getConstraintType())) {
					foreignKeys.add(metadata);
				}
			}
			for (Metadata rConstraintName : foreignKeys) {
				BaseEntity baseEntity =  dao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());
				
				Metadata metadata = (Metadata) baseEntity;
				if("R".equals(metadata.getConstraintType())){
					//String primaryKey = getPrimaryKeyForEntity();
					String primaryKey = getPrimaryKeyForEntity();
					BaseDAO newdao = new BaseDAO();
					List<BaseEntity> childObjects = newdao.read(metadata.getTableName().replace("_", ".").trim(), metadata.getRColumn().trim(), BaseDAO.EXPRESSION_EQUALS, primaryKey,true);
					newdao.close();
					for (BaseEntity childObject : childObjects) {
						if (!childObject.isNull() && "NODELETE".equalsIgnoreCase(metadata.getDeleteRule())){
							throw new ValidationFailedException(primaryKey + " found in child table "+metadata.getTableName());
						} else if (!childObject.isNull() && "CASCADE".equalsIgnoreCase(metadata.getDeleteRule())){
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
	
	public Map<String, String> getReferencedKeyFieldForForeignKey() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		Map<String, String> map = new HashMap<String, String>();
		BaseDAO newdao = new BaseDAO();
		List<BaseEntity> list = newdao.read("harsha.thesis.api.solution3.entity.Metadata", "TableName", BaseDAO.EXPRESSION_EQUALS, columnFamily, true);
		List<Metadata> foreignKeys = new LinkedList<Metadata>();
		
		for (BaseEntity baseEntity : list) {
			

			
			Metadata metadata = (Metadata) baseEntity;
			
			if ("F".equals(metadata.getConstraintType())) {
				foreignKeys.add(metadata);
			}
		}
		
		for (Metadata rConstraintName : foreignKeys) {
			BaseEntity baseEntity =  newdao.read("harsha.thesis.api.solution3.entity.Metadata", rConstraintName.getRConstraintName());
			
			Metadata metadata = (Metadata) baseEntity;
			if("R".equals(metadata.getConstraintType())){
				map.put(metadata.getTableName(), metadata.getRColumn());

			}
		}
		newdao.close();
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
				foreignKey = (String) method.invoke(entity);
				break;
			}
		}
		
		
		return foreignKey;
	}

}
