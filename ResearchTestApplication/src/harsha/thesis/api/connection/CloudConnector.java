package harsha.thesis.api.connection;

import org.apache.log4j.Logger;

public class CloudConnector {
	
	   private static Logger logger = Logger.getLogger(CloudConnector.class.getName());
           public  static int NUMBER_OF_CONNECTIONS = 0;
	
	//private static Connection con = null;
	
	
	private CloudConnector(){
		
	}
	
	public static Connection getConnection(ConnectionDefinition conDef) throws Exception{
            logger.info("Asking forr connection }");
            NUMBER_OF_CONNECTIONS++;
		Class<Connection> temp = (Class<Connection>) Class.forName(conDef.getConnectionClass());
		Connection con = null;
			try{
				
				con = temp.newInstance();
				con.open(conDef);
			} catch (Exception e){
				if (con != null){
					con.close();
				}
				throw new Exception(e.getMessage());
			}
		return con;
		
	}

        public static void returnConnection(Connection conn) {
            logger.info("Returning forr connection }");
            NUMBER_OF_CONNECTIONS--;
            if (conn != null) {
                try{
                conn.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }

}
