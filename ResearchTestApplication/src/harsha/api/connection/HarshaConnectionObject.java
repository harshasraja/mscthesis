/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.connection;

import harsha.api.connection.CloudConnector;
import harsha.api.connection.Connection;
import harsha.api.connection.ConnectionDefinition;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author harshasraja
 */
public class HarshaConnectionObject implements Connection {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ConnectionDefinition connectionDefinition;
    private Mutator<String> mutator;
    private RangeSlicesQuery<String, String, String> rangeSlicesQuery;
    private SliceQuery<String, String, String> sliceQuery;
    private IndexedSlicesQuery<String, String, String> indexedSlicesQuery;
    private Keyspace keyspaceObject;
    private Cluster cluster;
    private static StringSerializer stringSerializer = StringSerializer.get();
    /**
     *
     */
    private static HarshaConnectionObject CLUSTER_CONNECTION = null;
    private static HarshaConnectionObject METADATA_CONNECTION = null;

    public static HarshaConnectionObject GetClusterConnection() {
        if (CLUSTER_CONNECTION == null) {
            CLUSTER_CONNECTION = new HarshaConnectionObject(CloudConnector.DEFAULT_CONNECTION_DEFINITION);
            CLUSTER_CONNECTION.create();
        }
        return CLUSTER_CONNECTION;
    }

    public static HarshaConnectionObject GetMetadataConnection() {
        if (METADATA_CONNECTION == null) {
            METADATA_CONNECTION = new HarshaConnectionObject(CloudConnector.METADATA_CONNECTION_DEFINITION);
            METADATA_CONNECTION.create();
        }
        return METADATA_CONNECTION;
    }

    private HarshaConnectionObject(ConnectionDefinition connDef) {
        this.connectionDefinition = connDef;
    }

    protected void create() {
        logger.debug("Creating Cluster.......");
        CassandraHostConfigurator cassandraConfigurator = new CassandraHostConfigurator(connectionDefinition.getIpAndPort());

        this.cluster = HFactory.getOrCreateCluster(connectionDefinition.getClusterName(), cassandraConfigurator);
        logger.warn("Active pool size:" + this.cluster.getConnectionManager().getActivePools().size());
        //this.cluster.getConnectionManager().getActivePools().
        if (this.cluster.getConnectionManager().getActivePools().size() < 1) {
            close();
            throw new RuntimeException("Could not connect to:" + connectionDefinition.getConnectionString());
        }
        logger.info("Cluster created");

        this.keyspaceObject = HFactory.createKeyspace(connectionDefinition.getKeySpace(), getCluster());

        logger.debug("Creating mutator.......");
        this.mutator = HFactory.createMutator(getKeyspace(), StringSerializer.get());
        logger.info("Mutator Created.");


        logger.debug("Creating RangeSliceQuery.......");
        this.rangeSlicesQuery = HFactory.createRangeSlicesQuery(getKeyspace(), stringSerializer, stringSerializer, stringSerializer);
        logger.info("RangeSliceQuery Created.");

        logger.debug("Creating SliceQuery.......");
        this.sliceQuery = HFactory.createSliceQuery(getKeyspace(), stringSerializer, stringSerializer, stringSerializer);
        logger.info("SliceQuery Created.");

        logger.debug("Creating IndexedSlicesQuery.......");
//        this.indexedSlicesQuery = HFactory.createIndexedSlicesQuery(getKeyspace(), stringSerializer, stringSerializer, stringSerializer);
        logger.info("Indexed SlicesQuery Created.");

        logger.info("Connected to [IP Address]" + connectionDefinition.getIpAddress()
                + " [Port]" + connectionDefinition.getPort() + " [Cluster Name]"
                + connectionDefinition.getClusterName() + " [Key Space]" + connectionDefinition.getKeySpace());
    }

    @Override
    public void close() {
    }

    @Override
    public void open(ConnectionDefinition conDef) throws Exception {
    }

    @Override
    public Mutator<String> getMutator() throws Exception {

        return mutator;
    }

    @Override
    public RangeSlicesQuery<String, String, String> getRangeSliceQuery() {
        return this.rangeSlicesQuery;
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }

    @Override
    public SliceQuery<String, String, String> getSliceQuery() {
        return this.sliceQuery;
    }

    @Override
    public IndexedSlicesQuery<String, String, String> getIndexedSlicesQuery() {
//        return this.indexedSlicesQuery; This is buggy in hector as after one query it doesn't work for a different one
        return HFactory.createIndexedSlicesQuery(getKeyspace(), stringSerializer, stringSerializer, stringSerializer);
    }

    @Override
    public boolean isConnected() {
        if (null == getCluster()
                || this.cluster.getConnectionManager().getActivePools().size() < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Keyspace getKeyspace() {
        return this.keyspaceObject;
    }

    public static void main(String[] args) throws Exception {
        Connection c = CloudConnector.getConnection();
        System.out.println(c.getMutator().getClass().getName());
        CloudConnector.shutdown();

    }
}
