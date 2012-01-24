package harsha.thesis.api.solution3.dao;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.CloudConnector;
import harsha.thesis.api.connection.Connection;
import harsha.thesis.api.solution3.dao.ValidationHandler;
import harsha.thesis.api.solution3.entity.BaseEntity;
import harsha.thesis.api.solution3.entity.Metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.log4j.Logger;



public class BaseDAO {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected Connection connection = null;
	
	public static final String EXPRESSION_EQUALS = "=";
	public static final String EXPRESSION_LT = "<";
	public static final String EXPRESSION_GT = ">";
	private static final String EXPRESSION_NE = "<>";
	public static final String EXPRESSION_LE = "<=";
	public static final String EXPRESSION_GE = "=>";
	private String connectionString = "";
	private String driverClassName = "";
	
	
	
	
	protected BaseDAO(){
		
	}
	
	public BaseDAO(String driverClassName, String connectionString) throws Exception{
		logger.debug("Instantiating "+this.getClass().getName());
		this.connectionString = connectionString;
		this.driverClassName = driverClassName;
		connection = CloudConnector.getConnection(driverClassName, connectionString);

	}
	
	
	
	public String getConnectionString() {
		return connectionString;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void close(){
		if (connection != null){
			connection.close();
		}
	}
	
	
	
	public List<BaseEntity> read(String type) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		logger.info("Inside read with parameters [Type]:"+type);
		Class<BaseEntity> tempClass = (Class<BaseEntity>) Class.forName(type);
		
		List<BaseEntity> list = new LinkedList<BaseEntity>(); 
		
		BaseEntity entity = tempClass.newInstance();
		String primaryKey = getPrimaryKeyFieldForEntity(entity);
		Method [] methods = entity.getClass().getDeclaredMethods();
		String columnFamily = entity.getColumnFamilyRepresentation();
		
		logger.info("Column family in RangeSliceQuery:"+columnFamily);
		
		RangeSlicesQuery<String, String, String> rangeSlicesQuery  = connection.getRangeSliceQuery();
		rangeSlicesQuery.setColumnFamily( columnFamily );
		rangeSlicesQuery.setKeys("", "");
		rangeSlicesQuery.setRange("", "", false, 9);
		
		rangeSlicesQuery.setRowCount(1000);
		
		QueryResult<OrderedRows<String,String, String>> result = rangeSlicesQuery.execute();
		
		
		Rows<String, String, String> orderRows = result.get();
		
		
		for (Row<String, String, String> row : orderRows) {
			
			logger.debug("Key="+row.getKey()+"::> ");
			List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
			for (HColumn<String, String> hColumn : columns) {
				for (Method method : methods) {
					if (method.getName().equals("set"+hColumn.getName())) {
						method.invoke(entity, hColumn.getValue());
					} else if (method.getName().equals("set"+primaryKey)){
						method.invoke(entity, row.getKey());
					}
				}
				
				logger.debug(hColumn.getName()+":"+hColumn.getValue()+">>");
			}
			list.add(entity);
			entity = tempClass.newInstance();
		
		}
		
		return list;
	}
	
	public BaseEntity read(String type, String key) throws Exception{
		logger.info("Inside read with parameters [Type]:"+type+" [key]:"+key);
		
		Class<BaseEntity> tempClass = (Class<BaseEntity>) Class.forName(type);
		if (null == key || "".equals(key.trim())){
			throw new Exception("Invalid Key:"+key);
		}
		
		BaseEntity entity = tempClass.newInstance();
		String primaryKey = getPrimaryKeyFieldForEntity(entity);
		Method [] methods = entity.getClass().getDeclaredMethods();
		String columnFamily = entity.getColumnFamilyRepresentation();
		
		logger.info("Column family in RangeSliceQuery:"+columnFamily);
		
		SliceQuery<String, String, String> sliceQuery = connection.getSliceQuery();
		sliceQuery.setColumnFamily(columnFamily);
		sliceQuery.setRange("", "", false, methods.length);
		sliceQuery.setKey(key);
		
		QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
		
		ColumnSlice<String, String> columnSlice = result.get();
		
		
		List<HColumn<String, String>> columns = columnSlice.getColumns();
		
		//entity = null;
		for (HColumn<String, String> hColumn : columns) {
			//entity = tempClass.newInstance();
			for (Method method : methods) {
				if (method.getName().equals("set"+hColumn.getName()) &&
						!method.getName().contains("primaryKey")) {
					method.invoke(entity, hColumn.getValue());
					//break;
				} else if (method.getName().equals("set"+primaryKey)){
					method.invoke(entity, key);
					//break;
				}
			}
			
			logger.debug(hColumn.getName()+":"+hColumn.getValue()+">>");
		}
		
		return entity;
	}
	
	public List<BaseEntity> read(String type, String columnName, String expression, String columnValue, boolean returnAllRows) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
		logger.info("Inside read with parameters [Type]:"+type+ " [column name]:"+columnName+" [Expression]:"+expression+" [column value]:"+columnValue);
		List<BaseEntity> list = new LinkedList<BaseEntity>();
		Class<BaseEntity> tempClass = (Class<BaseEntity>) Class.forName(type);
		
		if (null == columnName || "".equals(columnName.trim())){
			throw new Exception("Invalid column name:"+columnName);
		}
		if (null == expression || "".equals(expression.trim())){
			throw new Exception("Invalid expression:"+expression);
		}
		
		
		IndexedSlicesQuery<String, String, String> indexedSlicesQuery = connection.getIndexedSlicesQuery();
		BaseEntity entity = tempClass.newInstance();
		Method [] methods = entity.getClass().getDeclaredMethods();
		String primaryKey = getPrimaryKeyFieldForEntity(entity);
		String columnFamily = entity.getColumnFamilyRepresentation();
		
		logger.info("Column family in IndexedSliceQuery:"+columnFamily);

		if (EXPRESSION_EQUALS.equals(expression)){
			indexedSlicesQuery.addEqualsExpression(columnName, columnValue);
		} else if (EXPRESSION_GT.equals(expression)){
			indexedSlicesQuery.addGtExpression(columnName, columnValue);
		} else if (EXPRESSION_LT.equals(expression)){
			indexedSlicesQuery.addLtExpression(columnName, columnValue);
		} else if (EXPRESSION_NE.equals(expression)){
			//indexedSlicesQuery.
		} else if (EXPRESSION_LE.equals(expression)){
			indexedSlicesQuery.addLteExpression(columnName, columnValue);
		} else if (EXPRESSION_GE.equals(expression)){
			indexedSlicesQuery.addGteExpression(columnName, columnValue);
		} else {
			throw new Exception("Invalid expression:"+expression);
		}
		if (returnAllRows){
			List<String> columnNames = new LinkedList<String>();
			for (Method method : methods) {
				if (method.getName().contains("set")){
					columnNames.add(method.getName().substring(3,method.getName().length()));
				}
			}
			indexedSlicesQuery.setColumnNames(columnNames);
		} else {
			indexedSlicesQuery.setColumnNames(columnName);
		}
		
		//indexedSlicesQuery.
		indexedSlicesQuery.setColumnFamily(columnFamily);
		indexedSlicesQuery.setStartKey("");
		
		QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery.execute();
		
		Rows<String, String, String> orderRows = result.get();
		
		
		for (Row<String, String, String> row : orderRows) {
			logger.debug("Key="+row.getKey()+"::> ");
			List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
			for (HColumn<String, String> hColumn : columns) {
				for (Method method : methods) {
					if (method.getName().equals("set"+hColumn.getName())) {
						method.invoke(entity, hColumn.getValue());
						//break;
					} else if (method.getName().equals("set"+primaryKey)){
						method.invoke(entity, row.getKey());
						//break;
					}
				}
				
				logger.debug(hColumn.getName()+":"+hColumn.getValue()+">>");
			}
			list.add(entity);
			entity = tempClass.newInstance();;
		}
		
			
			
		return list;
	}
	
	public void insert(BaseEntity entity) throws Exception{
		
		logger.debug("Inside insert the detected column family is:"+entity.getColumnFamilyRepresentation());
		String strEntity = "";
		strEntity = getStringRepresentationForLogging(entity);
		logger.info("Starting to insert:"+strEntity);
		
		Method [] methods = entity.getClass().getDeclaredMethods();
		Annotation [] a1 = entity.getClass().getDeclaredAnnotations();
		ValidationHandler helper = new ValidationHandler(entity, this);
		String primaryKey = null;
		String key = null;
		
		
		
		//This section gets the logical primary key field for the entity
		//from previously declared annotations for the entity
		// ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
		// ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
		// ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED
		
		for (Annotation annotation : a1) {
			//System.out.println(annotation);
			if (annotation instanceof PrimaryKey) {
				primaryKey = ((PrimaryKey) annotation).primaryKey();
				break;
			}
		}
		
		// This section fetches the actual primary key value from the entity class 
		// this section uses dynamic method invocation  
		// ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
		// ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
		// ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED
		
		for (Method method : methods) {
			if (method.getName().contains(primaryKey) && 
					method.getName().equals("get"+primaryKey)) {
				key = (String) method.invoke(entity, null);
				break;
			}
		}
		
		try {
		
			// This would check the referenced key except for Metadata Table
			//helper.checkUniqueKey();
			if (!(entity instanceof Metadata)){
				helper.checkReferenedKey();
			}
			
			Mutator<String> mutator = connection.getMutator();
			
			
			for (Method method : methods) {
				if (!method.getName().substring(3,method.getName().length()).equalsIgnoreCase(primaryKey)){
					if (method.getName().contains("get") && 
							!method.getName().contains("ColumnFamilyRepresentation")&&
							!method.getName().contains("KeyForUpdate")){
						mutator.addInsertion(key, entity.getColumnFamilyRepresentation(), 
								HFactory.createStringColumn(method.getName().substring(3), 
										(String)method.invoke(entity)));
						//method.i
					}
				}	
			}
			
			MutationResult me =mutator.execute();
		} catch (HInvalidRequestException e) {
			if (e.getMessage().contains("why:unconfigured columnfamily")) {
				logger.info(e.getMessage()+" hence trying to creating same");
				createColumFamily(entity);
				
				insert(entity);
				
			} else {
				logger.error(e.getMessage(), e);
			}
		}
		
		
		logger.debug("Finished insert the detected column family is:"+entity.getColumnFamilyRepresentation());
		logger.info("Finished inserting entity:"+strEntity);
		
		
	}
	
	public void delete(BaseEntity entity) throws Exception{
		String strEntity = "";
		strEntity = getStringRepresentationForLogging(entity);
		logger.info("Starting to delete:"+strEntity);
		
		ValidationHandler helper = new ValidationHandler(entity, this);
		
		if (!(entity instanceof Metadata)){
			helper.checkForeignKey();
		}
		
		String key = null;
		
		Method [] methods = entity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().contains(getPrimaryKeyFieldForEntity(entity)) && method.getName().contains("get")) {
				key = (String) method.invoke(entity);
			}
		}
		
		
		try {
			connection.getMutator().delete(key, entity.getColumnFamilyRepresentation(), null, StringSerializer.get());
		} catch (Exception ex) {
			if (ex.getMessage().contains("Attempt to borrow on in-active pool")) {
				connection = CloudConnector.getConnection(driverClassName, connectionString);
				connection.getMutator().delete(key, entity.getColumnFamilyRepresentation(), null, StringSerializer.get());
			} else {
				throw new Exception(ex);
			}
		}
		logger.info("Finished delete entity:"+strEntity);
	}
	
	public void update(BaseEntity entity) throws Exception{
		logger.info("Starting to update:"+getStringRepresentationForLogging(entity));
		
		if (null != entity.getKeyForUpdate() &&
				!"".equals(entity.getKeyForUpdate().trim())){
		
			Method [] methods = entity.getClass().getDeclaredMethods();
			Annotation [] a1 = entity.getClass().getDeclaredAnnotations();
			//ValidationHandler helper = new ValidationHandler(entity, this);
			String primaryKey = null;
			String key = null;
			//Method tempMethod = entity.getC
			
			
			//This section gets the logical primary key field for the entity
			//from previously declared annotations for the entity
			// ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
			// ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
			// ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED
			
			for (Annotation annotation : a1) {
				//System.out.println(annotation);
				if (annotation instanceof PrimaryKey) {
					primaryKey = ((PrimaryKey) annotation).primaryKey();
					break;
				}
			}
			
			// This section fetches the actual primary key value from the entity class 
			// this section uses dynamic method invocation  
			// ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
			// ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
			// ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED
			
			for (Method method : methods) {
				if (method.getName().contains(primaryKey) && 
						method.getName().equals("get"+primaryKey)){
					key = (String)method.invoke(entity);
					break;
				}
			}
			
			
			if (!read(entity.getClass().getName(), key).isNull()) {
				ValidationHandler handler = new ValidationHandler(entity, this);
				List<List<BaseEntity>> childObjectList = handler.checkForeignKeyForUpdate();
				Map<String, String> map = handler.getReferencedKeyFieldForForeignKey();
				delete(entity);
				
				for (Method method : methods) {
					if (method.getName().contains(primaryKey) && 
							method.getName().equals("set"+primaryKey)) {
						method.invoke(entity, entity.getKeyForUpdate());
						break;
					}
				}
				
				
				insert(entity);
				for (List<BaseEntity> list : childObjectList) {
					Method mtd = entity.getClass().getDeclaredMethod("get"+primaryKey);
					
					String changedValue = (String)mtd.invoke(entity);
					for (BaseEntity baseEntity : list) {
						String referencedKey = map.get(baseEntity.getColumnFamilyRepresentation());
						Method tempMethod = baseEntity.getClass().getDeclaredMethod("set"+referencedKey,mtd.getReturnType());
						tempMethod.invoke(baseEntity, changedValue);
						insert(baseEntity);
					}
				}
			} else {
				logger.info("Update record not found; hence exiting");
			}
		} else {
			logger.info("Update key not specified; hence exiting");
		}
		logger.info("Finished update entity:"+getStringRepresentationForLogging(entity));
	}

	private String getStringRepresentationForLogging(BaseEntity entity) {
		//This section is done purely for logging purposes
		Method [] methods = entity.getClass().getDeclaredMethods();
		String strEntity = "{";
		for (Method method : methods) {
			if (method.getName().contains("get")){
				try {
					strEntity = strEntity+";"+method.getName().substring(3,method.getName().length())+":"+(String) method.invoke(entity);
				} catch (IllegalArgumentException e) {
					logger.error("Exception thrown:", e);
				} catch (IllegalAccessException e) {
					logger.error("Exception thrown:", e);
				} catch (InvocationTargetException e) {
					logger.error("Exception thrown:", e);
				}
			}
			
		}
		strEntity = strEntity+"}";
		
		return strEntity;
	}
	
	private String getPrimaryKeyFieldForEntity(BaseEntity entity){
		String primaryKey = null;
		Annotation [] a1 = entity.getClass().getDeclaredAnnotations();
		for (Annotation annotation : a1) {
			//System.out.println(annotation);
			if (annotation instanceof PrimaryKey) {
				primaryKey = ((PrimaryKey) annotation).primaryKey();
			}
		}
		return primaryKey;
	}
	
	
	private void createColumFamily(BaseEntity entity){
		
		String primaryKey = getPrimaryKeyFieldForEntity(entity);
		
		
		
		BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
		columnFamilyDefinition.setKeyspaceName(connection.getKeySpace());
		columnFamilyDefinition.setName(entity.getColumnFamilyRepresentation());
		columnFamilyDefinition.setComparatorType(ComparatorType.UTF8TYPE);
		
		//me.prettyprint.cassandra.model.BasicColumnFamilyDefinition cannot be cast to me.prettyprint.cassandra.service.ThriftCfDef
		
		ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
		
		//columnFamilyDefinition = new BasicColumnFamilyDefinition(cfDef);
		
		BasicColumnDefinition columnDefinition = null;
		
		columnDefinition = new BasicColumnDefinition();
		columnDefinition.setName(StringSerializer.get().toByteBuffer(primaryKey));
		columnDefinition.setIndexName(entity.getColumnFamilyRepresentation()+"_"+primaryKey);
	    columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
	    columnDefinition.setValidationClass("UTF8Type");
	    cfDef.addColumnDefinition(columnDefinition);
	    
	    Method [] methods = entity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!method.getName().contains("set"+primaryKey) && 
					!method.getName().contains("get"+primaryKey) && 
					!method.getName().contains("ColumnFamilyRepresentation") &&
					!method.getName().contains("KeyForUpdate") &&
					method.getName().contains("get")) {
				
				columnDefinition = new BasicColumnDefinition();
				columnDefinition.setName(StringSerializer.get().toByteBuffer(method.getName().substring(3,method.getName().length())));
				//columnDefinition.setValidationClass("UTF8Type");
				columnDefinition.setIndexName(entity.getColumnFamilyRepresentation()+"_"+method.getName().substring(3,method.getName().length()));
				columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
			    columnDefinition.setValidationClass("UTF8Type");
			    cfDef.addColumnDefinition(columnDefinition);
			}
		}
		
		connection.getCluster().addColumnFamily(cfDef);
	    
	}

	
	

}
