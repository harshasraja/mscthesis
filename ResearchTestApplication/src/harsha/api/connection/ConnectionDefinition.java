package harsha.api.connection;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ConnectionDefinition {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private String clusterName;
	private String ipAddress;
	private String keySpace;
	private int port;
	private String connectionString;
	private String ipAndPort;
	private String connectionClass;
	
	protected ConnectionDefinition(){
		
	}
	
	public ConnectionDefinition(String connectionString, String connectionClass) {
		
		
		this.connectionClass = connectionClass;
		this.connectionString = connectionString;
		logger.info("Connection String:"+connectionString);
		//  ipaddress:port@clusterName/keySpace
		
		StringTokenizer tokenizer = new StringTokenizer(connectionString,"@");
		if (tokenizer.countTokens() != 2){
			throw new RuntimeException("Invalid connection String:"+connectionString);
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
		
	}
	
	

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getKeySpace() {
		return keySpace;
	}

	public void setKeySpace(String keySpace) {
		this.keySpace = keySpace;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getIpAndPort() {
		return ipAndPort;
	}

	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}

	public String getConnectionClass() {
		return connectionClass;
	}

	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}
	
	
	
	
	

}
