package harsha.thesis.app.java;

import harsha.thesis.api.connection.ConnectionDefinition;
import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.api.exception.ValidationFailedException;
import harsha.thesis.api.solution0.dao.BaseDAO;
import harsha.thesis.api.solution0.entity.BaseEntity;
import harsha.thesis.api.solution0.helper.CSVReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class JavaApplication {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//192.168.43.2:9160@testCluster/UNIVERSITY harsha.thesis.app.java.Solution0 com.thesis.api.solution0.entity.Metadata <OPERATION> /Users/vinay/Documents/workspace/ResearchTestApplication/data/Solution3/Metadata.csv
		//-Dlog4j.properties=log4j.xml
		

		BaseSolution sol = null;
		try {
			Class tempClass = Class.forName(args[1]);
			ConnectionDefinition conDef = new ConnectionDefinition(args[0], HectorConnectionObject.class.getName());
			sol = (BaseSolution)tempClass.newInstance();
			sol.setConnectionDefinition(conDef);
			sol.setArgs(args);
			sol.run();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
		sol = null;
		System.gc();
		System.exit(0);

		
		
		//(new JavaApplication()).run(args);

	}
	
	public void run(String[] args){
		BaseDAO dao = null;
		try {
			dao = null;//new BaseDAO(null);
			CSVReader csvReader = new CSVReader();
			List<BaseEntity> entities = csvReader.getEntities(args[3], args[1]);
			
			for (BaseEntity entity : entities) {
				try{
					dao.insert(entity);
				} catch (ValidationFailedException ex) {
					logger.error(ex.getMessage(), ex);
				}
			}
			//Course course = new Course();
			//course.setCourseId("COMP300");
			//dao.delete(course);
			//User user = new User();
			//user.setUserId("100");
			//dao.delete(user);
			
			//Inside read with parameters [Type]:harsha.thesis.api.solution3.entity.Enrolment [column name]:UserId [Expression]:= [column value]:100
			//Inside read with parameters [Type]:harsha.thesis.api.solution3.entity.Enrolment [column name]:UserId [Expression]:= [column value]:100
			
			//printEntity(dao.read("harsha.thesis.api.solution3.entity.Metadata", "CONST500"));
			//printEntities(dao.read(args[1], "CourseName", BaseDAO.EXPRESSION_EQUALS, "Software Engineering Basics",true));
			//printEntities(dao.read("harsha.thesis.api.solution3.entity.Enrolment", "UserId", BaseDAO.EXPRESSION_EQUALS, "100",true));
			printEntities(dao.read(args[1]));
			//printEntity(dao.read(args[1],"NWEN200"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (null != dao) {
				dao.close();
			}
		}
	}
	
	public void printEntities(List<BaseEntity> entities) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
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
