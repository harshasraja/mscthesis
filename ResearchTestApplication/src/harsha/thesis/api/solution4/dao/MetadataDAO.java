package harsha.thesis.api.solution4.dao;

import harsha.thesis.api.connection.ConnectionDefinition;

public class MetadataDAO extends BaseDAO {

	public MetadataDAO() {

	}

	public MetadataDAO(ConnectionDefinition connectionDefinition)
			throws Exception {
		super(connectionDefinition, null);
		logger.info("Created DAO");
	}
	
	
	
	

}
