package harsha.thesis.api.solution;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.CloudConnector;
import harsha.thesis.api.connection.Connection;
import harsha.thesis.api.solution.entity.Entity;


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
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.log4j.Logger;

public class EntityManager {

    protected static final Logger logger = Logger.getLogger(EntityManager.class);
    public static final String EXPRESSION_EQUALS = "=";
    public static final String EXPRESSION_LT = "<";
    public static final String EXPRESSION_GT = ">";
    private static final String EXPRESSION_NE = "<>";
    public static final String EXPRESSION_LE = "<=";
    public static final String EXPRESSION_GE = "=>";
    protected Connection connection;
    protected ValidationHandler validationHandler;

    public EntityManager(ValidationHandler validationHandler) throws Exception {
        this.connection = CloudConnector.getConnection();
        this.validationHandler = validationHandler;
    }

    public void setValidationHandler(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    public ValidationHandler getValidationHandler() {
        return this.validationHandler;
    }

    public void close() {
        CloudConnector.returnConnection(connection);
    }

    protected void loadEntity(Entity entity, List<HColumn<String, String>> columns)
            throws Exception {
        Method[] methods = entity.getClass().getMethods();
        for (HColumn<String, String> hColumn : columns) {
            for (Method method : methods) {
                if (method.getName().equals("set" + hColumn.getName())) {
                    method.invoke(entity, hColumn.getValue());
                }
            }
        }
        entity.setMetadata(validationHandler.retrieveMetadata());
    }

    public List<Entity> read(Class<? extends Entity> clazz) throws Exception {
        return read(clazz, Integer.MAX_VALUE);
    }

    public List<Entity> read(Class<? extends Entity> clazz, int number) throws Exception {
        List<Entity> list = new ArrayList<Entity>();

        Entity entity = clazz.newInstance();

        RangeSlicesQuery<String, String, String> rangeSlicesQuery = connection.getRangeSliceQuery();
        rangeSlicesQuery.setColumnFamily(entity.getColumnFamily());
        rangeSlicesQuery.setKeys("", "");
        rangeSlicesQuery.setRange("", "", false, clazz.getMethods().length); //TODO: Check if the size of range is correct
        rangeSlicesQuery.setRowCount(number);


        QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery.execute();


        Rows<String, String, String> orderRows = result.get();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            loadEntity(entity, columns);
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
    public Entity find(Class<? extends Entity> clazz, String id) throws Exception {
        Entity entity = clazz.newInstance();

        SliceQuery<String, String, String> sliceQuery = connection.getSliceQuery();
        sliceQuery.setColumnFamily(entity.getColumnFamily());
        sliceQuery.setRange("", "", false, clazz.getMethods().length); //TODO: Check if the size of range is correct
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
        ColumnSlice<String, String> columnSlice = result.get();
        List<HColumn<String, String>> columns = columnSlice.getColumns();

        loadEntity(entity, columns);

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
    public List<Entity> query(Class<? extends Entity> clazz,
            String columnName, String expression, String columnValue) throws Exception {

        IndexedSlicesQuery<String, String, String> indexedSlicesQuery = connection.getIndexedSlicesQuery();
        indexedSlicesQuery.setStartKey("");
        if (EXPRESSION_EQUALS.equals(expression)) {
            indexedSlicesQuery.addEqualsExpression(columnName, columnValue);
        } else {
            if (EXPRESSION_GT.equals(expression)) {
                indexedSlicesQuery.addGtExpression(columnName, columnValue);
            } else {
                if (EXPRESSION_LT.equals(expression)) {
                    indexedSlicesQuery.addLtExpression(columnName, columnValue);
                } else {
                    if (EXPRESSION_NE.equals(expression)) {
                        //???
                    } else {
                        if (EXPRESSION_LE.equals(expression)) {
                            indexedSlicesQuery.addLteExpression(columnName, columnValue);
                        } else {
                            if (EXPRESSION_GE.equals(expression)) {
                                indexedSlicesQuery.addGteExpression(columnName, columnValue);
                            } else {
                                throw new Exception("Invalid expression:" + expression);
                            }
                        }
                    }
                }
            }
        }

//        List<String> columnNames = new ArrayList<String>();
//        Annotation[] annotations = clazz.getDeclaredAnnotations();
//        for (Annotation a : annotations) {
//            if (a instanceof Column) {
//                columnNames.add(((Column) a).columnName());
//            }
//        }
//        indexedSlicesQuery.setColumnNames(columnNames);

        Entity entity = clazz.newInstance();
        indexedSlicesQuery.setColumnFamily(entity.getColumnFamily());

        List<Entity> list = new ArrayList<Entity>();
        QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery.execute();
        Rows<String, String, String> orderRows = result.get();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            loadEntity(entity, columns);
            list.add(entity);
            entity = clazz.newInstance();
        }

        return list;
    }

    public void insert(Entity entity) throws Exception {
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

            for (Annotation a : entity.getClass().getDeclaredAnnotations()) {
                if (a instanceof Column) {
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

    public void delete(Entity entity) throws Exception {
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

    public void update(Entity entity) throws Exception {
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

                } else {
                    if (method.getName().contains(primaryKey)
                            && method.getName().equals("get" + primaryKey)) {
                        key = (String) method.invoke(entity);
                    }
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

    private String getStringRepresentationForLogging(Entity entity) {
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

    private String getPrimaryKeyFieldForEntity(Entity entity) {
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

    public void createColumFamily(Entity entity) {

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
