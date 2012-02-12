package harsha.thesis.api.solution;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.CloudConnector;
import harsha.thesis.api.connection.Connection;
import harsha.thesis.api.solution.entity.BaseEntity;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

public class EntityManager {

    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected Connection connection = null;
    public static final String EXPRESSION_EQUALS = "=";
    public static final String EXPRESSION_LT = "<";
    public static final String EXPRESSION_GT = ">";
    private static final String EXPRESSION_NE = "<>";
    public static final String EXPRESSION_LE = "<=";
    public static final String EXPRESSION_GE = "=>";

    public EntityManager() throws Exception {
        connection = CloudConnector.getConnection();
    }

    public void close() {
        CloudConnector.returnConnection(connection);
    }

    public List<BaseEntity> find(Class<? extends BaseEntity> clazz) throws Exception {
        List<BaseEntity> list = new ArrayList<BaseEntity>();

        BaseEntity entity = clazz.newInstance();

        RangeSlicesQuery<String, String, String> rangeSlicesQuery = connection.getRangeSliceQuery();
        rangeSlicesQuery.setColumnFamily(entity.getColumnFamily());
        rangeSlicesQuery.setKeys("", "");
        Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
        List<String> columnNames = new ArrayList<String>();
        for (Annotation a : annotations) {
            if (a instanceof Column) {
                columnNames.add(((Column) a).columnName());
            }
        }

        rangeSlicesQuery.setColumnNames((String[]) columnNames.toArray());

        rangeSlicesQuery.setRowCount(1000);

        QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery.execute();


        Rows<String, String, String> orderRows = result.get();


        Method[] methods = entity.getClass().getDeclaredMethods();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            for (HColumn<String, String> hColumn : columns) {
                for (Method method : methods) {
                    if (method.getName().equals("set" + hColumn.getName())) {
                        method.invoke(entity, hColumn.getValue());
                    }
                }
            }
            list.add(entity);
            entity = clazz.newInstance();

        }

        return list;
    }

    /**
     * For reading the details of a particlar key. Returns only a single entity
     * for this key. For eg., returning the details where CoureID=SWEN100.
     * Returns all columns for this key
     *
     * @param type
     * @param key
     * @return
     * @throws Exception
     */
    public BaseEntity find(Class<? extends BaseEntity> clazz, String id) throws Exception {


        BaseEntity entity = clazz.newInstance();

        Method[] methods = entity.getClass().getDeclaredMethods();

        SliceQuery<String, String, String> sliceQuery = connection.getSliceQuery();
        sliceQuery.setColumnFamily(entity.getColumnFamily());

        
        List<String> columnNames = new ArrayList<String>();
        Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof Column) {
                columnNames.add(((Column) a).columnName());
            }
        }
        sliceQuery.setColumnNames((String[]) columnNames.toArray());
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<String, String>> results = sliceQuery.execute();

        ColumnSlice<String, String> columnSlice = results.get();

        if (columnSlice.getColumns().size() != columnNames.size()) {
            logger.warn("columnSlice.getColumns().size() != columnNames.size()");
        }
        List<HColumn<String, String>> columns = columnSlice.getColumns();

        for (HColumn<String, String> hColumn : columns) {
            for (Method method : methods) {
                if (method.getName().equals("set" + hColumn.getName())) {
                    method.invoke(entity, hColumn.getValue());
                }
            }
        }
        return entity;
    }
/**
     * Processes the expression for conditional lookups in printAll statements
     * For example, where courseName = SWEN100
     *
     * @param type Which entity is being operated upon i.e., course or user etc
     * @param columnName Which column name is expressed in the condition, eg,
     * courseName
     * @param expression - Is it =, <,> etc.
     * @param columnValue - what value are we checking for. eg., SWEN100
     * @param returnAllRows - True/False. True-returns all columns in result
     * else returns only queried column eg., courseName
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public List<BaseEntity> query(Class<? extends BaseEntity> clazz,
            String columnName, String expression, String columnValue) throws Exception {

        IndexedSlicesQuery<String, String, String> indexedSlicesQuery = connection.getIndexedSlicesQuery();

        if (EXPRESSION_EQUALS.equals(expression)) {
            indexedSlicesQuery.addEqualsExpression(columnName, columnValue);
        } else if (EXPRESSION_GT.equals(expression)) {
            indexedSlicesQuery.addGtExpression(columnName, columnValue);
        } else if (EXPRESSION_LT.equals(expression)) {
            indexedSlicesQuery.addLtExpression(columnName, columnValue);
        } else if (EXPRESSION_NE.equals(expression)) {
            //???
        } else if (EXPRESSION_LE.equals(expression)) {
            indexedSlicesQuery.addLteExpression(columnName, columnValue);
        } else if (EXPRESSION_GE.equals(expression)) {
            indexedSlicesQuery.addGteExpression(columnName, columnValue);
        } else {
            throw new Exception("Invalid expression:" + expression);
        }

        List<BaseEntity> list = new ArrayList<BaseEntity>();

        BaseEntity entity = clazz.newInstance();
        Method[] methods = entity.getClass().getDeclaredMethods();

        List<String> columnNames = new ArrayList<String>();
        Annotation[] annotations = entity.getClass().getDeclaredAnnotations();
        for (Annotation a : annotations) {
            if (a instanceof Column) {
                columnNames.add(((Column) a).columnName());
            }
        }
        indexedSlicesQuery.setColumnNames(columnNames);
        
        indexedSlicesQuery.setColumnFamily(entity.getColumnFamily());
        indexedSlicesQuery.setStartKey("");

        QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery.execute();

        Rows<String, String, String> orderRows = result.get();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            for (HColumn<String, String> hColumn : columns) {
                for (Method method : methods) {
                    if (method.getName().equals("set" + hColumn.getName())) {
                        method.invoke(entity, hColumn.getValue());
                    } 
                }
            }
            list.add(entity);
            entity = clazz.newInstance();
        }

        return list;
    }

    
    
    public void insert(BaseEntity entity) throws Exception {
//I AM HERE
        Method[] methods = entity.getClass().getDeclaredMethods();
        Annotation[] a1 = entity.getClass().getDeclaredAnnotations();
        
        
        //This section gets the logical primary key field for the entity
        //from previously declared annotations for the entity
        // ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
        // ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
        // ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED

//        for (Annotation annotation : a1) {
//            //System.out.println(annotation);
//            if (annotation instanceof PrimaryKey) {
//                primaryKey = ((PrimaryKey) annotation).primaryKey();
//                break;
//            }
//        }

        // This section fetches the actual primary key value from the entity class
        // this section uses dynamic method invocation
        // ** IT IS ASSUMED THAT THERE WOULD BE ONLY ONE PRIMARY KEY & NO COMPOSITE PRIMARYKEY
        // ** IF THERE IS A COMPOSITE PRIMARY KEY, LOGIC WOULD NEED TO BE REVISTED
        // ** AND BREAK STATEMENT INSIDE IF CONDITION SHOULD BE REMOVED

        
        try {

            // This would check the referenced key except for Metadata Table
            //helper.checkUniqueKey();
			/*
             * if (!(entity instanceof Metadata)){ helper.checkReferenedKey(); }
             */

            Mutator<String> mutator = connection.getMutator();

            for (Annotation a : entity.getClass().getDeclaredAnnotations()){
                if (a instanceof Column){
                    
                }
            }
            
            for (Method method : methods) {
//                if (!method.getName().substring(3, method.getName().length()).equalsIgnoreCase(primaryKey)) {
                    if (method.getName().contains("get")
                            && !method.getName().contains("ColumnFamilyRepresentation")
                            && !method.getName().contains("KeyForUpdate")) {
//                        mutator.addInsertion(key, entity.getColumnFamily(),
//                                HFactory.createStringColumn(method.getName().substring(3),
//                                (String) method.invoke(entity)));
                        //method.i
//                    }
                }
            }

            MutationResult me = mutator.execute();
        } catch (HInvalidRequestException e) {
            if (e.getMessage().contains("why:unconfigured columnfamily")) {
                logger.info(e.getMessage() + " hence trying to creating same");
                createColumFamily(entity);

                insert(entity);

            } else {
                logger.error(e.getMessage(), e);
            }
        }


        logger.debug("Finished insert the detected column family is:" + entity.getColumnFamily());
        logger.debug("Finished inserting entity:" + getStringRepresentationForLogging(entity));


    }

    public void delete(BaseEntity entity) throws Exception {
        //String strEntity = "";
        //strEntity = getStringRepresentationForLogging(entity);
        logger.debug("Starting to delete:" + getStringRepresentationForLogging(entity));

        //ValidationHandler helper = new ValidationHandler(entity, this);

        /*
         * if (!(entity instanceof Metadata)){ helper.checkForeignKey(); }
         */

        String key = null;

        Method[] methods = entity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().contains(getPrimaryKeyFieldForEntity(entity)) && method.getName().contains("get")) {
                key = (String) method.invoke(entity);
            }
        }



        connection.getMutator().delete(key, entity.getColumnFamily(), null, StringSerializer.get());
        //connection.getMutator().
        logger.debug("Finished delete entity:" + getStringRepresentationForLogging(entity));
    }

    public void update(BaseEntity entity) throws Exception {
        logger.debug("Starting to update:" + getStringRepresentationForLogging(entity));

        if (null != entity.getKeyForUpdate()
                && !"".equals(entity.getKeyForUpdate().trim())) {

            Method[] methods = entity.getClass().getDeclaredMethods();
            Annotation[] a1 = entity.getClass().getDeclaredAnnotations();
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
                if (method.getName().contains(primaryKey)
                        && method.getName().equals("set" + primaryKey)) {
                    method.invoke(entity, entity.getKeyForUpdate());

                } else if (method.getName().contains(primaryKey)
                        && method.getName().equals("get" + primaryKey)) {
                    key = (String) method.invoke(entity);
                }
            }

            if (!find(entity.getClass(), key).isNull()) {
                delete(entity);
                insert(entity);
            } else {
                logger.debug("Update record not found; hence exiting");
            }
        } else {
            logger.debug("Update key not specified; hence exiting");
        }
        logger.debug("Finished update entity:" + getStringRepresentationForLogging(entity));
    }

    private String getStringRepresentationForLogging(BaseEntity entity) {
        //This section is done purely for logging purposes
        Method[] methods = entity.getClass().getDeclaredMethods();
        String strEntity = "{";
        for (Method method : methods) {
            if (method.getName().contains("get")) {
                try {
                    strEntity = strEntity + ";" + method.getName().substring(3, method.getName().length()) + ":" + (String) method.invoke(entity);
                } catch (IllegalArgumentException e) {
                    logger.error("Exception thrown:", e);
                } catch (IllegalAccessException e) {
                    logger.error("Exception thrown:", e);
                } catch (InvocationTargetException e) {
                    logger.error("Exception thrown:", e);
                }
            }

        }
        strEntity = strEntity + "}";

        return strEntity;
    }

    private String getPrimaryKeyFieldForEntity(BaseEntity entity) {
        String primaryKey = null;
        Annotation[] a1 = entity.getClass().getDeclaredAnnotations();
        for (Annotation annotation : a1) {
            //System.out.println(annotation);
            if (annotation instanceof PrimaryKey) {
                primaryKey = ((PrimaryKey) annotation).primaryKey();
            }
        }
        return primaryKey;
    }

    public void createColumFamily(BaseEntity entity) {

        String primaryKey = getPrimaryKeyFieldForEntity(entity);



        BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
        columnFamilyDefinition.setKeyspaceName(connection.getKeyspace().getKeyspaceName());
        columnFamilyDefinition.setName(entity.getColumnFamily());
        columnFamilyDefinition.setComparatorType(ComparatorType.UTF8TYPE);

        //me.prettyprint.cassandra.model.BasicColumnFamilyDefinition cannot be cast to me.prettyprint.cassandra.service.ThriftCfDef

        ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);

        //columnFamilyDefinition = new BasicColumnFamilyDefinition(cfDef);

        BasicColumnDefinition columnDefinition = null;

        columnDefinition = new BasicColumnDefinition();
        columnDefinition.setName(StringSerializer.get().toByteBuffer(primaryKey));
        columnDefinition.setIndexName(entity.getColumnFamily() + "_" + primaryKey);
        columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
        columnDefinition.setValidationClass("UTF8Type");
        cfDef.addColumnDefinition(columnDefinition);

        Method[] methods = entity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().contains("set" + primaryKey)
                    && !method.getName().contains("get" + primaryKey)
                    && !method.getName().contains("ColumnFamilyRepresentation")
                    && !method.getName().contains("KeyForUpdate")
                    && method.getName().contains("get")) {

                columnDefinition = new BasicColumnDefinition();
                columnDefinition.setName(StringSerializer.get().toByteBuffer(method.getName().substring(3, method.getName().length())));
                //columnDefinition.setValidationClass("UTF8Type");
                columnDefinition.setIndexName(entity.getColumnFamily() + "_" + method.getName().substring(3, method.getName().length()));
                columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
                columnDefinition.setValidationClass("UTF8Type");
                cfDef.addColumnDefinition(columnDefinition);
            }
        }

        connection.getCluster().addColumnFamily(cfDef);

    }
}
