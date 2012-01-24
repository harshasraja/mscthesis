/**
 * 
 */
package harsha.thesis.api.connection.hector;



import harsha.thesis.api.connection.Connection;

import java.util.StringTokenizer;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
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
 * @author vinay
 *
 */
public class HectorConnectionObject implements Connection{
	
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String clusterName;
	private String ipAddress;
	private String keySpace;
	private int port;
	private String connectionString;
	private String ipAndPort;
	
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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void close() {
		logger.info("Closeing Connection Object......");
		//this.rangeSlicesQuery = null;
		//this.mutator = null;
		//cluster.getConnectionManager().shutdown();
		if (cluster != null){
			HFactory.shutdownCluster(cluster);
		}
		
		this.cluster = null;
		logger.info("Closed Connection Object.");
	}

	@Override
	public String getClusterName() {
		return clusterName;
	}
	
	@Override
	public String getConnectionString(){
		return connectionString;
	}

	@Override
	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public String getKeySpace() {
		return keySpace;
	}

	@Override
	public Mutator<String> getMutator() throws Exception {
		
		return mutator;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public RangeSlicesQuery<String, String, String> getRangeSliceQuery() {
		return this.rangeSlicesQuery;
	}

	@Override
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
		
	}

	@Override
	public void setConnectionString(String connectionString) throws Exception  {
		this.connectionString = connectionString;
		logger.info("Connection String:"+connectionString);
		//  ipaddress:port@clusterName/keySpace
		
		StringTokenizer tokenizer = new StringTokenizer(connectionString,"@");
		if (tokenizer.countTokens() != 2){
			throw new Exception("Invalid connection String:"+connectionString);
		}
		ipAndPort = tokenizer.nextToken();
		logger.debug("IP Address: Port:"+ipAndPort);
		String clusterNameAndKeySpace = tokenizer.nextToken();
		logger.debug("ClusterName and KeySpace:"+clusterNameAndKeySpace);
		
		tokenizer = new StringTokenizer(ipAndPort, ":");
		this.ipAddress = tokenizer.nextToken();
		this.port = Integer.parseInt(tokenizer.nextToken());
		
		tokenizer = new StringTokenizer(clusterNameAndKeySpace, "/");
		this.clusterName = tokenizer.nextToken();
		this.keySpace = tokenizer.nextToken();
		
		logger.debug("Creating Cluster.......");
		this.cluster = HFactory.getOrCreateCluster(this.clusterName, new CassandraHostConfigurator(ipAndPort));
		logger.warn("Active pool size:"+this.cluster.getConnectionManager().getActivePools().size());
		//this.cluster.getConnectionManager().getActivePools().
		if (this.cluster.getConnectionManager().getActivePools().size()<1){
			close();
			throw new Exception("Could not connect to:"+connectionString);
		}
		logger.info("Cluster created");
		
		logger.debug("Creating mutator.......");
		this.mutator = HFactory.createMutator(getKeyspaceObject(), StringSerializer.get());
		logger.info("Mutator Created.");
		
		
		logger.debug("Creating RangeSliceQuery.......");
		this.rangeSlicesQuery = HFactory.createRangeSlicesQuery(getKeyspaceObject(), stringSerializer, stringSerializer, stringSerializer);
		logger.info("RangeSliceQuery Created.");
		
		logger.debug("Creating SliceQuery.......");
		this.sliceQuery = HFactory.createSliceQuery(getKeyspaceObject(), stringSerializer, stringSerializer, stringSerializer);
		logger.info("SliceQuery Created.");
		
		logger.debug("Creating IndexedSlicesQuery.......");
		this.indexedSlicesQuery = HFactory.createIndexedSlicesQuery(getKeyspaceObject(), stringSerializer, stringSerializer, stringSerializer);
		logger.info("Indexed SlicesQuery Created.");
		
		logger.info("Connected to [IP Address]"+ipAddress+" [Port]"+port+" [Cluster Name]"+clusterName+" [Key Space]"+keySpace);
	}

	@Override
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		
	}

	@Override
	public void setKeySpace(String keySpace) {
		this.keySpace = keySpace;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
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
		if (null == getCluster() ||
				this.cluster.getConnectionManager().getActivePools().size()<1){
			return false;
		}
		return true;
	}

	@Override
	public Keyspace getKeyspaceObject() {
		this.keyspaceObject = HFactory.createKeyspace(getKeySpace(), getCluster());
		return this.keyspaceObject;
	}

}
