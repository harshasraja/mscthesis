package harsha.thesis.app.java;

import harsha.api.connection.CloudConnector;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution3.dao.BaseDAO;
import harsha.thesis.api.solution3.entity.BaseEntity;
import harsha.thesis.api.solution3.helper.CSVReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class Solution3 extends BaseSolution{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private BaseDAO dao;
	List<BaseEntity> entities;

	
	public Solution3(){

		
	}
	
	public void run() {
		try {
			this.dao = new BaseDAO();
			CSVReader reader = new CSVReader();
			if (args[3].equals("insert") ||
					args[3].equals("delete")||
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
		System.out.println("Connections: " + CloudConnector.NUMBER_OF_CONNECTIONS);
	}

	public void insert() throws Exception{
		
		int i=0;
		Long startTime = System.currentTimeMillis();
		for (BaseEntity baseEntity : entities) {
			try{
				this.dao = new BaseDAO();
				dao.insert(baseEntity);
				i++;
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			} finally {
				logger.info("************ Records inserted:"+i);
				if (null != dao) {
					dao.close();
				}
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
				this.dao = new BaseDAO();
				dao.delete(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			} finally {
				if (null != dao) {
					dao.close();
				}
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
	}
	
	public void update() throws Exception{
		
		Long startTime = System.currentTimeMillis();
		for (BaseEntity baseEntity : entities) {
			try{
				this.dao = new BaseDAO();
				dao.update(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			} finally {
				if (null != dao) {
					dao.close();
				}
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to delete "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
		
	}
	
	public void printAll() throws Exception{
		this.dao = new BaseDAO();
		printEntities(dao.read(args[2]));
		
	}
	
	public void printForKey() throws Exception{
		this.dao = new BaseDAO();
		printEntity(dao.read(args[2],args[4]));
	}
	
	public void printForColumn() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		this.dao = new BaseDAO();
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
