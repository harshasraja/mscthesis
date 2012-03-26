package harsha.api;

import harsha.api.connection.CloudConnector;
import harsha.api.connection.Connection;


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
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.log4j.Logger;

public class EntityManager {
    
    public enum Expression {
        
        EQUALS, LT, GT, NE, LE, GE;
        
        @Override
        public String toString() {
            switch (this) {
                case EQUALS:
                    return "=";
                case LT:
                    return "<";
                case GT:
                    return ">";
                case NE:
                    return "<>";
                case LE:
                    return "<=";
                case GE:
                    return "=>";
                default:
                    throw new AssertionError(this);
            }
        }
    }
    protected static final Logger LOG = Logger.getLogger(EntityManager.class);
    private Connection connection;
    protected ValidationHandler validationHandler;
    
    public EntityManager() {
        this(CloudConnector.getConnection());
    }
    
    public EntityManager(Connection connection) {
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
            if ("Metadata".equals(column)) {
                continue;
            }
            result.add(column);
        }
        return result;
    }
    
    public boolean loadEntity(Entity entity, List<HColumn<String, String>> columns)
            throws Exception {
        LOG.debug("Loading entity");
        for (HColumn<String, String> hColumn : columns) {
            Entity.SetValue(hColumn.getName(), hColumn.getValue(), entity);
        }
        LOG.debug("Loaded: " + entity);
        
        String id = Entity.GetValue(Entity.GetPrimaryKeyColumn(entity.getClass()), entity);
        return id != null && !id.trim().isEmpty();
    }
    
    public String columnFamily(Class<? extends Entity> clazz) {
        return validationHandler.solution() + "_" + Entity.GetColumnFamily(clazz);
    }
    
    public <T extends Entity> List<T> read(Class<T> clazz) throws Exception {
        return read(clazz, (int) 1e5);
    }
    
    public <T extends Entity> List<T> read(Class<T> clazz, int number) throws Exception {
        LOG.debug("Reading " + number + " records from " + columnFamily(clazz) + "");
        List<T> list = new ArrayList<T>();
        
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
            T entity = clazz.newInstance();
            boolean validEntity = loadEntity(entity, columns);
            if (validEntity) {
                list.add(entity);
            }
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
    public <T extends Entity> T find(Class<T> clazz, String id) throws Exception {
        LOG.debug("Finding " + columnFamily(clazz) + " with id=" + id);
        SliceQuery<String, String, String> sliceQuery = getConnection().getSliceQuery();
        sliceQuery.setColumnFamily(columnFamily(clazz));
        sliceQuery.setRange("", "", false, getColumnsFor(clazz).size());
        List<String> columnNames = getColumnsFor(clazz);
        sliceQuery.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
        sliceQuery.setKey(id);
        
        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
        ColumnSlice<String, String> columnSlice = result.get();
        List<HColumn<String, String>> columns = columnSlice.getColumns();
        
        T entity = clazz.newInstance();
        boolean validEntity = loadEntity(entity, columns);
        if (!validEntity) {
            entity = null;
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
    public <T extends Entity> List<T> query(Class<T> clazz,
            String columnName, Expression expression, String columnValue) throws Exception {
        LOG.debug("Querying column family " + columnFamily(clazz) + " where "
                + columnName + expression.toString() + columnValue);
        IndexedSlicesQuery<String, String, String> indexedSlicesQuery = getConnection().getIndexedSlicesQuery();
        indexedSlicesQuery.setStartKey("");
        
        switch (expression) {
            case EQUALS:
                indexedSlicesQuery.addEqualsExpression(columnName, columnValue);
                break;
            case GT:
                indexedSlicesQuery.addGtExpression(columnName, columnValue);
                break;
            case LT:
                indexedSlicesQuery.addLtExpression(columnName, columnValue);
                break;
            case NE:
                //????
                break;
            case LE:
                indexedSlicesQuery.addLteExpression(columnName, columnValue);
                break;
            case GE:
                indexedSlicesQuery.addGteExpression(columnName, columnValue);
                break;
            default:
                throw new AssertionError();
        }
        
        
        indexedSlicesQuery.setColumnFamily(columnFamily(clazz));
        indexedSlicesQuery.setColumnNames(getColumnsFor(clazz));

//        LOG.warn("TODO: check how to retrieve all columnNames");

        
        List<T> result = new ArrayList<T>();
        QueryResult<OrderedRows<String, String, String>> queryResult =
                indexedSlicesQuery.execute();
        Rows<String, String, String> orderRows = queryResult.get();
        LOG.debug("Retrieval of " + orderRows.getCount() + " rows");
        for (Row<String, String, String> row : orderRows) {
            List<HColumn<String, String>> columns = row.getColumnSlice().getColumns();
            T entity = clazz.newInstance();
            boolean validEntity = loadEntity(entity, columns);
            if (validEntity) {
                result.add(entity);
            }
        }
        
        return result;
    }
    
    public void insert(Entity entity) throws Exception {
        LOG.debug("Inserting: " + entity);
        validationHandler.onInsert(entity);
        
        List<String> columnNames = getColumnsFor(entity.getClass());
        
        Mutator<String> mutator = getConnection().getMutator();
        for (String column : columnNames) {
            mutator.addInsertion(
                    Entity.GetValue(Entity.GetPrimaryKeyColumn(entity.getClass()), entity),
                    columnFamily(entity.getClass()),
                    HFactory.createStringColumn(column, Entity.GetValue(column, entity)));
        }
        
        mutator.execute();
    }
    
    public void delete(Entity entity) throws Exception {
        LOG.debug("Deleting: " + entity);
        this.validationHandler.onDelete(entity);
        
        getConnection().getMutator().delete(
                Entity.GetValue(Entity.GetPrimaryKeyColumn(entity.getClass()), entity),
                columnFamily(entity.getClass()), null, StringSerializer.get());
    }
    
    public void update(Entity entity) throws Exception {
        LOG.debug("Updating: " + entity);
        
        if (entity.getKeyForUpdate() == null || entity.getKeyForUpdate().isEmpty()) {
            insert(entity);
            return;
        }
        
        Entity oldEntity = (Entity) entity.clone();
        
        
        String primaryKeyColumn = Entity.GetPrimaryKeyColumn(entity.getClass());
        String newId = entity.getKeyForUpdate();
        
        Entity.SetValue(primaryKeyColumn, newId, entity);
        entity.setKeyForUpdate(null);

        //insert entity with new id
        try {
            insert(entity);
        } catch (Exception ex) {
            //Rollback entity to what it was
            Entity.SetValue(primaryKeyColumn,
                    Entity.GetValue(primaryKeyColumn, oldEntity),
                    entity);
            entity.setKeyForUpdate(newId);
            throw ex;
        }


        //Update children with old Entity
        try {
            this.validationHandler.onUpdate(oldEntity);
        } catch (Exception ex) {
            delete(entity); //delete new entity;
            //rollback entity to what it was
            Entity.SetValue(primaryKeyColumn,
                    Entity.GetValue(primaryKeyColumn, oldEntity),
                    entity);
            entity.setKeyForUpdate(newId);
            throw ex;
        }

        //delete entity with oldId
        delete(oldEntity);
    }
    
    public void createColumFamily(Class<? extends Entity> clazz) throws Exception {
        createColumFamily(clazz, false);
    }
    
    public void createColumFamily(Class<? extends Entity> clazz, boolean dropIfExists) throws Exception {
        LOG.debug("Creating CF:" + columnFamily(clazz));
        KeyspaceDefinition keyspaceDefinition = getConnection().getCluster().describeKeyspace(
                getConnection().getKeyspace().getKeyspaceName());
        
        List<ColumnFamilyDefinition> columnFamilies = keyspaceDefinition.getCfDefs();
        for (ColumnFamilyDefinition columnFamily : columnFamilies) {
            if (columnFamily.getName().equals(columnFamily(clazz))) {
                if (!dropIfExists) {
                    return;
                }
                LOG.debug("Dropping existing CF: " + columnFamily.getName());
                try {
                    getConnection().getCluster().dropColumnFamily(
                            columnFamily.getKeyspaceName(), columnFamily.getName(), true);
                } catch (Exception ex) {
                    LOG.error("Error dropping existing CF: " + columnFamily.getName());
                }
            }
        }
        
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
        
        getConnection().getCluster().addColumnFamily(columnFamilyDefinition, true);
    }
}
