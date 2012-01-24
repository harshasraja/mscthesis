package harsha.thesis.api.solution2.entity;

import org.apache.log4j.Logger;

public abstract class BaseEntity {

	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	private String keyForUpdate;
	
	public String getKeyForUpdate() {
		return keyForUpdate;
	}

	public void setKeyForUpdate(String keyForUpdate) {
		this.keyForUpdate = keyForUpdate;
	}

	public String getColumnFamilyRepresentation(){
		return this.getClass().getName().replace('.', '_');
	}
	
	public abstract boolean isNull();
}
