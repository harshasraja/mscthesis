package harsha.api.connection;

import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

public interface Connection {

	
	public Mutator<String> getMutator() throws Exception;
	
	public RangeSlicesQuery<String, String, String> getRangeSliceQuery ();
	
	public void close();
	
	public Cluster getCluster();
	
	public SliceQuery<String, String, String> getSliceQuery();
	
	public IndexedSlicesQuery<String, String, String> getIndexedSlicesQuery();
	
	public boolean isConnected();
	
	public Keyspace getKeyspace();

	void open(ConnectionDefinition conDef) throws Exception;
	
}
