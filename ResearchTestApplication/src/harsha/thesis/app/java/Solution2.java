package harsha.thesis.app.java;

import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution2.dao.BaseDAO;
import harsha.thesis.api.solution2.entity.BaseEntity;
import harsha.thesis.api.solution2.helper.CSVReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class Solution2 extends BaseSolution{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private BaseDAO dao;
	List<BaseEntity> entities;
	
	public Solution2(){

		
	}
	
	public void run() {
		try {
			this.dao = new BaseDAO();
			CSVReader reader = new CSVReader();
			if (args[3].equals("insert") ||
					args[3].equals("delete") ||
					args[3].equals("update")){
				this.entities = reader.getEntities(args[4], args[2]);
			}
			Method [] methods = this.getClass().getDeclaredMethods();
			// Gets all the declared method in Solution3.java
			// Invokes the correct method according to the argument 3.
			// i.e, if it is Insert as the third argument then Solution3 Insert() isinvoked.
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
				logger.debug(baseEntity.getMetadataStringRepresentation());
				dao.insert(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.info("/*******************Time to insert "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
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
		logger.info("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
	}
	
	public void update() throws Exception{
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
		logger.info("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
	}
	
	public void printAll() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException{
		
		printEntities(dao.read(args[2]));
		
	}
	
	public void printForKey() throws Exception{
		printEntity(dao.read(args[2],args[4]));
	}
	
	public void printForColumn() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		printEntities(dao.read(args[2], args[4], args[5], args[6], Boolean.parseBoolean(args[7])));
	}
	
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
