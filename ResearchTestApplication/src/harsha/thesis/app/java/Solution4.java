package harsha.thesis.app.java;

import harsha.thesis.api.connection.ConnectionDefinition;
import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution4.dao.BaseDAO;
import harsha.thesis.api.solution4.dao.MetadataDAO;
import harsha.thesis.api.solution4.dao.ValidationHandler;
import harsha.thesis.api.solution4.entity.BaseEntity;
import harsha.thesis.api.solution4.helper.CSVReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class Solution4 extends BaseSolution{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private BaseDAO dao;
	List<BaseEntity> entities;
	
	public Solution4(){
		
		
	}
	
	public void run() {
		ConnectionDefinition metadataConDef = null;
		ValidationHandler handler = null;
		try {
			if (args[4].equals("insert") ||
					args[4].equals("delete") ||
					args[4].equals("update")){
				metadataConDef = new ConnectionDefinition(args[2], HectorConnectionObject.class.getName());
				if (!"harsha.thesis.api.solution4.entity.Metadata".equals(args[3])) {
					handler = new ValidationHandler(metadataConDef, conDef, args[3]);
				}				
			}
			
			if("harsha.thesis.api.solution4.entity.Metadata".equals(args[3])){
				this.dao = new MetadataDAO(metadataConDef);
			} else {
				this.dao = new BaseDAO(conDef,handler);
			}

			
			CSVReader reader = new CSVReader();
			if (args[4].equals("insert") ||
					args[4].equals("delete") ||
					args[4].equals("update")){
				this.entities = reader.getEntities(args[5], args[3]);
			}
			Method [] methods = this.getClass().getDeclaredMethods();
			// Gets all the declared method in Solution3.java
			// Invokes the correct method according to the argument 3.
			// i.e, if it is Insert as the third argument then Solution3 Insert() isinvoked.
			for (Method method : methods) {
				if (args[4].equals(method.getName())){
					method.invoke(this);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			
			if (null != dao) {
				dao.close();
			}
			
			if (null != handler){
				handler.closeHandler();
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
			try{
				dao.update(baseEntity);
			} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
			}
		}
		Long endTime = System.currentTimeMillis();
		Long timeTaken = endTime - startTime;
		logger.warn("/*******************Time to update "+entities.size()+" records of type "+args[2]+" is:"+timeTaken+" milli second");
		
	}
	
	public void printAll() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException{
		
		printEntities(dao.read(args[3]));
		
	}
	
	public void printForKey() throws Exception{
		printEntity(dao.read(args[3],args[5]));
	}
	
	public void printForColumn() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		printEntities(dao.read(args[3], args[5], args[6], args[7], Boolean.parseBoolean(args[8])));
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
