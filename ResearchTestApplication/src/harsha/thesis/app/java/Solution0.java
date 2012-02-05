package harsha.thesis.app.java;

import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution0.dao.BaseDAO;
import harsha.thesis.api.solution0.entity.BaseEntity;
import harsha.thesis.api.solution0.helper.CSVReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class Solution0 extends BaseSolution{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private BaseDAO dao;
	List<BaseEntity> entities;
	
	
	public Solution0(){
		
		
	}
	

	
	public void run() {
		try {
			this.dao = new BaseDAO(conDef);
			CSVReader reader = new CSVReader();
			if (args[3].equals("insert") ||
					args[3].equals("delete") ||
					args[3].equals("update")){
				this.entities = reader.getEntities(args[4], args[2]);
			}
			
			Method [] methods = this.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (args[3].equals(method.getName())){
					method.invoke(this);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != dao) {
				dao.close();
			}
		}
		
	}

	public void insert() throws Exception{
		
		
		Long startTime = System.currentTimeMillis();
		for (BaseEntity baseEntity : entities) {
			try{
				dao.insert(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to insert "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
	}
	
	public void delete() throws Exception{
		Long startTime = System.currentTimeMillis();
		for (BaseEntity baseEntity : entities) {
			try{
				dao.delete(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
	}
	
	public void update() throws Exception{
		
		Long startTime = System.currentTimeMillis();
		for (BaseEntity baseEntity : entities) {
				dao.update(baseEntity);
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
		
	}
	
	public void printAll() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException{
		
		printEntities(dao.read(args[2]));
		
	}
	
	/**
	 * For conditional statements to bring up details of a key.
	 * 
	 * @throws Exception
	 */
	public void printForKey() throws Exception{
		printEntity(dao.read(args[2],args[4]));
	}
	
	/**
	 * Query for a particular column. Like a Select statement
	 * Calls the read() in BaseDAO and passes entity type, column name, condition etc to the method
	 * age=21 or Trimester = 3 so returns a list
	 * This reult list is passed as arguments to printEntities
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public void printForColumn() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		printEntities(dao.read(args[2], args[4], args[5], args[6], Boolean.parseBoolean(args[7])));
	}
	
	/**Formatting method.
	 * Calls printEntity for each of the entities.
	 * 
	 * @param entities
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void printEntities(List<BaseEntity> entities) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		for (BaseEntity baseEntity : entities) {
			printEntity(baseEntity);
		}
	}

	private void printEntity(BaseEntity baseEntity)
			throws IllegalAccessException, InvocationTargetException {
		String strEntity = baseEntity.getColumnFamilyRepresentation()+"{";
		Method [] methods = baseEntity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().contains("get") && 
					!method.getName().contains("ColumnFamilyRepresentation")){
				
								strEntity = strEntity + method.getName().substring(3,method.getName().length())+":"+(String)method.invoke(baseEntity)+";";

			}
		}
		
		strEntity = strEntity + "}";
		
		logger.info(strEntity);
	}
}
