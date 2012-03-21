package harsha.thesis.api.solution.em;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.connection.CloudConnector;
import harsha.thesis.api.connection.Connection;
import harsha.thesis.api.solution.vh.ValidationHandler;
import harsha.thesis.api.solution.entity.Entity;


import java.lang.reflect.Field;
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

    protected static final Logger LOG = Logger.getLogger(EntityManager.class);
    public static final String EXPRESSION_EQUALS = "=";
    public static final String EXPRESSION_LT = "<";
    public static final String EXPRESSION_GT = ">";
    public static final String EXPRESSION_NE = "<>";
    public static final String EXPRESSION_LE = "<=";
    public static final String EXPRESSION_GE = "=>";
    private Connection connection;
    protected ValidationHandler validationHandler;

    public EntityManager() {
        this(CloudConnector.getConnection());
    }
    
    public EntityManager(Connection connection){
        this.connection = connection;
    }

    public void setValidationHandler(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    public ValidationHandler getValidationHandler() {
        return this.validationHandler;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void close() {
        CloudConnector.returnConnection(getConnection());
    }

    public List<String> getColumnsFor(Class<? extends Entity> clazz) {
        List<String> result = new ArrayList<String>();
        for (String column : Entity.GetAllColumnsFor(clazz)) {
            if ("Metadata".equals(column)
                    && !"solution1".equals(validationHandler.solution())) {
                continue;
            }
            result.add(column);
        }
        return result;
    }

    public void loadEntity(Entity entity, List<HColumn<String, String>> columns)
            throws Exception {
        for (HColumn<String, String> hColumn : columns) {
            Entity.SetValue(hColumn.getName(), hColumn.getValue(), entity);
        }
    }

    public String columnFamily(Class<? extends Entity> clazz) {
        return validationHandler.solution() + "_" + Entity.GetName(clazz);
    }

    public List<Entity> read(Class<? extends Entity> clazz) throws Exception {
        return read(clazz, Integer.MAX_VALUE);
    }

    public List<Entity> read(Class<? extends Entity> clazz, int number) throws Exception {
        List<Entity> list = new ArrayList<Entity>();

        RangeSlicesQuery<String, String, String> rangeSlicesQuery = getConnection().getRangeSliceQuery();
        rangeSlicesQuery.setColumnFamily(columnFamily(clazz));
        rangeSlicesQuery.setKeys("", "");
        rangeSlicesQuery.setRange("", "", false, getColumnsFor(clazz).size());
        LOG.warn("TODO:Check if the size of range is correct, clazz.methods.length");
        List<String> columnNames = getColumnsFor(clazz);
        rangeSlicesQuery.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
        rangeSlicesQuery.setRowCount(number);

        QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery.execute();

        Rows<String, String, String> orderRows = result.get();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            Entity entity = clazz.newInstance();
            loadEntity(entity, columns);
            list.add(entity);
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
        SliceQuery<String, String, String> sliceQuery = getConnection().getSliceQuery();
        sliceQuery.setColumnFamily(columnFamily(clazz));
        sliceQuery.setRange("", "", false, getColumnsFor(clazz).size());
        LOG.warn("TODO:Check if the size of range is correct, clazz.methods.length");
        List<String> columnNames = getColumnsFor(clazz);
        sliceQuery.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
        sliceQuery.setKey(id);

        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
        ColumnSlice<String, String> columnSlice = result.get();
        List<HColumn<String, String>> columns = columnSlice.getColumns();

        Entity entity = clazz.newInstance();
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

        IndexedSlicesQuery<String, String, String> indexedSlicesQuery = getConnection().getIndexedSlicesQuery();
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


        indexedSlicesQuery.setColumnFamily(columnFamily(clazz));
        indexedSlicesQuery.setColumnNames(getColumnsFor(clazz));
        LOG.warn("TODO: check how to retrieve all columnNames");



        List<Entity> list = new ArrayList<Entity>();
        QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery.execute();
        Rows<String, String, String> orderRows = result.get();
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            Entity entity = clazz.newInstance();
            loadEntity(entity, columns);
            list.add(entity);
        }

        return list;
    }

    public void insert(Entity entity) throws Exception {

        validationHandler.onInsert(entity);

        List<String> columnNames = getColumnsFor(entity.getClass());


        Mutator<String> mutator = getConnection().getMutator();
        for (String column : columnNames) {
            mutator.addInsertion(
                    Entity.GetValue(Entity.GetPrimaryKey(entity.getClass()), entity),
                    columnFamily(entity.getClass()),
                    HFactory.createStringColumn(column, Entity.GetValue(column, entity)));
        }

        MutationResult me = null;
        try {
            me = mutator.execute();
        } catch (HInvalidRequestException e) {
            if (e.getMessage().contains("why:unconfigured columnfamily")) {
                LOG.info("Creating column family: " + columnFamily(entity.getClass()));
                createColumFamily(entity.getClass());
                insert(entity);
            }
        }
    }

    public void delete(Entity entity) throws Exception {

        this.validationHandler.onDelete(entity);

        List<Entity> dependencies = validationHandler.retrieveDependencies(entity);
        for (Entity dependency : dependencies) {
            delete(dependency);
        }

        getConnection().getMutator().delete(
                Entity.GetValue(Entity.GetPrimaryKey(entity.getClass()), entity),
                columnFamily(entity.getClass()), null, StringSerializer.get());

    }

    public void update(Entity entity) throws Exception {

        this.validationHandler.onUpdate(entity);

        if (entity.getKeyForUpdate() == null || entity.getKeyForUpdate().isEmpty()) {
            insert(entity);
            return;
        }

        //Retrieve entities with old id.
        List<Entity> dependencies = this.validationHandler.retrieveDependencies(entity);

        String primaryKey = Entity.GetPrimaryKey(entity.getClass());
        String oldId = Entity.GetValue(primaryKey, entity);
        String newId = entity.getKeyForUpdate();

        Entity.SetValue(primaryKey, newId, entity);
        entity.setKeyForUpdate(null);
        //insert entity with new id
        insert(entity);

        //update dependencies to new foreign id.
        for (Entity dependency : dependencies) {
            Entity.SetValue(Entity.GetName(entity.getClass()) + "Id",
                    newId, dependency);

            update(dependency);
        }

        //delete entity with oldId
        Entity.SetValue(primaryKey, oldId, entity);
        delete(entity);
    }

    public void createColumFamily(Class<? extends Entity> clazz) {

        BasicColumnFamilyDefinition columnFamily = new BasicColumnFamilyDefinition();
        columnFamily.setKeyspaceName(getConnection().getKeyspace().getKeyspaceName());
        columnFamily.setName(columnFamily(clazz));
        columnFamily.setComparatorType(ComparatorType.UTF8TYPE);

        ColumnFamilyDefinition columnFamilyDefinition = new ThriftCfDef(columnFamily);

        List<String> columns = getColumnsFor(clazz);

        for (String column : columns) {
            BasicColumnDefinition columnDefinition = new BasicColumnDefinition();
            columnDefinition.setName(StringSerializer.get().toByteBuffer(column));
            columnDefinition.setIndexName("ix_" + columnFamily(clazz) + "_" + column);
            columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
            columnDefinition.setValidationClass("UTF8Type");
            columnFamilyDefinition.addColumnDefinition(columnDefinition);
        }

        getConnection().getCluster().addColumnFamily(columnFamilyDefinition);
    }
}
