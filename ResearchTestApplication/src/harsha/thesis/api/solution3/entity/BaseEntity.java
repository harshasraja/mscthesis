package harsha.thesis.api.solution3.entity;

import org.apache.log4j.Logger;

public abstract class BaseEntity {

	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected String keyForUpdate;
	
	public String getColumnFamilyRepresentation(){
		return this.getClass().getName().replace('.', '_');
	}
	
	
	
	public abstract String getKeyForUpdate();



	public abstract void setKeyForUpdate(String keyForUpdate);



	public abstract boolean isNull();
}
