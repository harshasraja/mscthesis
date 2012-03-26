/**
 * 
 */
package harsha.api.connection;

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
 * @author harshasraja
 *
 */
public class HectorConnectionObject implements Connection {

    private Logger logger = Logger.getLogger(this.getClass().getName());
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
    public HectorConnectionObject() {
    }

    @Override
    public void close() {
        logger.info("Closing Connection Object......");
        //this.rangeSlicesQuery = null;
        //this.mutator = null;
        //cluster.getConnectionManager().shutdown();
//        if (cluster != null) {
//            HFactory.shutdownCluster(cluster);
//        }

        this.cluster = null;
        logger.info("Closed Connection Object.");
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
    public void open(ConnectionDefinition conDef) throws Exception {
        logger.debug("Creating Cluster.......");
        CassandraHostConfigurator cassandraConfigurator = new CassandraHostConfigurator(conDef.getIpAndPort());

        this.cluster = HFactory.getOrCreateCluster(conDef.getClusterName(), cassandraConfigurator);
        logger.warn("Active pool size:" + this.cluster.getConnectionManager().getActivePools().size());
        //this.cluster.getConnectionManager().getActivePools().
        if (this.cluster.getConnectionManager().getActivePools().size() < 1) {
            close();
            throw new Exception("Could not connect to:" + conDef.getConnectionString());
        }
        logger.info("Cluster created");

        this.keyspaceObject = HFactory.createKeyspace(conDef.getKeySpace(), getCluster());

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
        this.indexedSlicesQuery = HFactory.createIndexedSlicesQuery(getKeyspace(), stringSerializer, stringSerializer, stringSerializer);
        logger.info("Indexed SlicesQuery Created.");

        logger.info("Connected to [IP Address]" + conDef.getIpAddress() + " [Port]" + conDef.getPort() + " [Cluster Name]" + conDef.getClusterName() + " [Key Space]" + conDef.getKeySpace());
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
        return this.indexedSlicesQuery;
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
}
