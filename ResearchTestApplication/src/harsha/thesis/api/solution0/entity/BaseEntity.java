package harsha.thesis.api.solution0.entity;

import org.apache.log4j.Logger;

public abstract class BaseEntity {

	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected String keyForUpdate;
	
	public abstract String getKeyForUpdate();

	public abstract void setKeyForUpdate(String keyForUpdate);
	
	public abstract String getColumnFamilyRepresentation();
	
	public abstract boolean isNull();
	
}
