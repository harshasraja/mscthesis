package harsha.thesis.api.solution1.helper;

import harsha.api.annotation.PrimaryKey;
import harsha.thesis.api.solution1.entity.BaseEntity;

import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class CSVReader {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private List<String[]> myData = null;
	
	private Annotation [] annotations;
	
	private Method [] methods;
	
	private String [] columnHeaders;
	
	private Class<BaseEntity> baseEntity;
	
	private void open(URI url) throws IOException{
		logger.debug("Opening file:"+url.getPath());
		au.com.bytecode.opencsv.CSVReader reader = new au.com.bytecode.opencsv.CSVReader(new FileReader(url.getPath()));
		this.myData = reader.readAll();
		logger.info("Finished Opening file:"+url.getPath());
	}
	
	public List<BaseEntity> getEntities(String csvFilePath, String entityType) throws URISyntaxException, IOException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		logger.info("Loading entity for file:"+csvFilePath);
		List<BaseEntity> list = new LinkedList<BaseEntity>();
		
		URI url = new URI(csvFilePath);
		open(url);
		this.baseEntity = (Class<BaseEntity>) Class.forName(entityType);
		
		logger.info("Detected entity type:"+this.baseEntity.getName());
		this.annotations = baseEntity.getDeclaredAnnotations();
		this.methods = baseEntity.getDeclaredMethods();
		this.columnHeaders = myData.get(0);
		
		int i=0;
		for (String[] data : myData) {
			if (i>0){
				list.add(getEntity(data));
			}
			i++;
		}
		

		logger.info("Populated list of "+baseEntity.getName()+" with "+list.size()+" records");
		return list;
	}
	
	private BaseEntity getEntity(String [] data) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		logger.debug("Inside getEntity");
		BaseEntity tempEntity = baseEntity.newInstance();
		String pkColumnName = "";
		for (Annotation annotation : annotations) {
			if(annotation instanceof PrimaryKey){
				 pkColumnName = ((PrimaryKey) annotation).primaryKey();
				populatePrimaryKeyColumn(data, tempEntity, pkColumnName);
			}
			
		}
		
		populateColumn(data, tempEntity, pkColumnName);
		
		logger.debug("Leaving getEntity");
		return tempEntity;
		
		
	}

	private void populatePrimaryKeyColumn(String[] data, BaseEntity tempEntity,
			String column) throws IllegalAccessException,
			InvocationTargetException {
		logger.debug("Inside populatePrimaryKeyColumn");
		for (int i=0; i<columnHeaders.length; i++) {
			if(column.equals(columnHeaders[i].trim())){
				for (Method method : methods) {
					if (method.getName().contains(column) && 
							method.getName().equals("set"+column)) {
							logger.debug("Invoking method:"+method.getName()+" with Value:"+data[i]);
							method.invoke(tempEntity, data[i]);
							break;
					}
				}
			}
		}
		logger.debug("Leaving PopulatePrimaryKeyColumn");
	}
	
	private void populateColumn(String[] data, BaseEntity tempEntity, String pkColumn) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		logger.debug("Inside populateColumns");
		for (int i=0; i<columnHeaders.length; i++) {
			//logger.debug("Value of i:"+i);
			if(!pkColumn.equals(columnHeaders[i].trim())){
				for (Method method : methods) {
					logger.debug("[pkColumn]:"+pkColumn+" [Method Name]:"+method.getName()+" [columnHeaders["+i+"]]:"+columnHeaders[i]+" [data["+i+"]]"+data[i]);
					if (method.getName().contains(columnHeaders[i]) && 
							method.getName().equals("set"+columnHeaders[i]) &&
							!pkColumn.contains(method.getName())) {
							//logger.debug("Value of i:"+i);
							//logger.debug("Invoking method:"+method.getName()+" with Value:"+data[i]);
							method.invoke(tempEntity, data[i]);
							break;
					}
				}
			}
		}
		logger.debug("Leaving populateColumns");
	}

}
