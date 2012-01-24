package harsha.thesis.api.connection;

import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

public interface Connection {

	public String getIpAddress();
	
	public void setIpAddress(String ipAddress);
	
	public int getPort();
	
	public void setPort(int port);
	
	public String getClusterName();
	
	public void setClusterName(String clusterName);
	
	public String getKeySpace();
	
	public void setKeySpace(String keySpace);
	
	public Mutator<String> getMutator() throws Exception;
	
	public RangeSlicesQuery<String, String, String> getRangeSliceQuery ();
	
	public void close();
	
	public void setConnectionString(String connectionString) throws Exception;
	
	public String getConnectionString();
	
	public Cluster getCluster();
	
	public SliceQuery<String, String, String> getSliceQuery();
	
	public IndexedSlicesQuery<String, String, String> getIndexedSlicesQuery();
	
	public boolean isConnected();
	
	public Keyspace getKeyspaceObject();
	
}
