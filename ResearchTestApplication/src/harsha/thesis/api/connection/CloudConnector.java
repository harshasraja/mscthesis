package harsha.thesis.api.connection;

import org.apache.log4j.Logger;

public class CloudConnector {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	//private static Connection con = null;
	
	
	private CloudConnector(){
		
	}
	
	public static Connection getConnection(String connectionClass, String connectionString) throws Exception{
		Class<Connection> temp = (Class<Connection>) Class.forName(connectionClass);
		//if (con == null ||
		//		!con.isConnected()) {
		Connection con = null;
			try{
				con = null;
				con = temp.newInstance();
				con.setConnectionString(connectionString);
			} catch (Exception e){
				if (con != null){
					con.close();
				}
				throw new Exception(e.getMessage());
			}
		//}
		return con;
		
	}

}
