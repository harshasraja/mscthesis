package harsha.thesis.api.connection;

import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.exp.Main;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.log4j.Logger;

public class CloudConnector {

    private static Logger logger = Logger.getLogger(CloudConnector.class.getName());
    public static int NUMBER_OF_CONNECTIONS = 0;
    public static ConnectionDefinition DEFAULT_CONNECTION_DEFINITION =
            new ConnectionDefinition(Main.HECTOR_CONNECTION, HectorConnectionObject.class.getName());
    public static ConnectionDefinition METADATA_CONNECTION_DEFINITION =
            new ConnectionDefinition(Main.METADATA_CONNECTION, HectorConnectionObject.class.getName());

    //private static Connection con = null;
    private CloudConnector() {
    }

    public static Connection getConnection() throws Exception {
        NUMBER_OF_CONNECTIONS++;
        Class<Connection> temp = (Class<Connection>) Class.forName(DEFAULT_CONNECTION_DEFINITION.getConnectionClass());
        Connection con = null;
        try {
            con = temp.newInstance();
            con.open(DEFAULT_CONNECTION_DEFINITION);
        } catch (Exception e) {
            if (con != null) {
                con.close();
            }
            throw new Exception(e.getMessage());
        }
        return con;

    }

    public static Connection getConnection(ConnectionDefinition conDef) throws Exception {
        NUMBER_OF_CONNECTIONS++;
        Class<Connection> temp = (Class<Connection>) Class.forName(conDef.getConnectionClass());
        Connection con = null;
        try {

            con = temp.newInstance();
            con.open(conDef);
        } catch (Exception e) {
            if (con != null) {
                con.close();
            }
            throw new Exception(e.getMessage());
        }
        return con;

    }

    public static void returnConnection(Connection conn) {
        NUMBER_OF_CONNECTIONS--;
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void shutdown() {
        CassandraHostConfigurator cassandraConfigurator = new CassandraHostConfigurator(DEFAULT_CONNECTION_DEFINITION.getIpAndPort());
        Cluster cluster = HFactory.getOrCreateCluster(DEFAULT_CONNECTION_DEFINITION.getClusterName(), cassandraConfigurator);
        cluster.getConnectionManager().shutdown();

        Cluster metadataCluster = HFactory.getCluster(METADATA_CONNECTION_DEFINITION.getClusterName());
        if (metadataCluster != null) {
            metadataCluster.getConnectionManager().shutdown();
        }
    }
}
