package harsha.thesis.api.connection;

import harsha.thesis.api.connection.hector.HarshaConnectionObject;
import harsha.thesis.exp.Main;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

public class CloudConnector {

    public static int NUMBER_OF_CONNECTIONS = 0;
    public static ConnectionDefinition DEFAULT_CONNECTION_DEFINITION =
            new ConnectionDefinition(Main.HECTOR_CONNECTION, null);
    public static ConnectionDefinition METADATA_CONNECTION_DEFINITION =
            new ConnectionDefinition(Main.METADATA_CONNECTION, null);

    //private static Connection con = null;
    private CloudConnector() {
    }

    public static Connection getConnection() throws Exception {
        NUMBER_OF_CONNECTIONS++;
        return HarshaConnectionObject.GetClusterConnection();
//        Class<Connection> temp = (Class<Connection>) Class.forName(DEFAULT_CONNECTION_DEFINITION.getConnectionClass());
//        Connection con = null;
//        try {
//            con = temp.newInstance();
//            con.open(DEFAULT_CONNECTION_DEFINITION);
//        } catch (Exception e) {
//            if (con != null) {
//                con.close();
//            }
//            throw new Exception(e.getMessage());
//        }
//        return con;

    }

    public static Connection getMetadataConnection(ConnectionDefinition conDef) throws Exception {
        NUMBER_OF_CONNECTIONS++;
        return HarshaConnectionObject.GetMetadataConnection();
//        Class<Connection> temp = (Class<Connection>) Class.forName(conDef.getConnectionClass());
//        Connection con = null;
//        try {
//
//            con = temp.newInstance();
//            con.open(conDef);
//        } catch (Exception e) {
//            if (con != null) {
//                con.close();
//            }
//            throw new Exception(e.getMessage());
//        }
//        return con;

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
        Cluster cluster = HFactory.getCluster(DEFAULT_CONNECTION_DEFINITION.getClusterName());
        if (cluster != null) {
            HFactory.shutdownCluster(cluster);
        }

        Cluster metadataCluster = HFactory.getCluster(METADATA_CONNECTION_DEFINITION.getClusterName());
        if (metadataCluster != null) {
            HFactory.shutdownCluster(metadataCluster);
        }


    }
}
