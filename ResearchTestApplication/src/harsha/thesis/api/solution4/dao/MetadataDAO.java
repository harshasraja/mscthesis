package harsha.thesis.api.solution4.dao;

public class MetadataDAO extends BaseDAO {

	public MetadataDAO() {

	}

	public MetadataDAO(String driverClassName, String connectionString)
			throws Exception {
		super(driverClassName, connectionString);
		logger.info("Created DAO");
	}
	
	

}
