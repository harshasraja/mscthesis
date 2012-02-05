package harsha.thesis.api.solution2.helper;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.solution2.entity.BaseEntity;

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

/**
 * @author vinay
 *
 */
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

	/**
	 * To populate the PK of all entities. 
	 * The Setter methods are invoked and values from CSV inserted accordingly
	 * i.e, the courseID in Course.java gets values from the file.
	 * 
	 * @param data
	 * @param tempEntity
	 * @param column
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
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
	
	/**
	 * To populate the remaining columns of the entities.
	 * Setter methods are invoked of the entity and 
	 * the values are set.
	 * 
	 * @param data
	 * @param tempEntity
	 * @param pkColumn
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
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
