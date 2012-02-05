package harsha.thesis.api.solution0.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;

@PrimaryKey(primaryKey="RowId")
public class Enrolment extends BaseEntity {
	
	private String rowId;
	
	@Column(columnName="UserId")
	private String userId;
	
	@Column(columnName="CourseId")
	private String courseId;
	
	@Column(columnName="Type")
	private String type;

	public Enrolment() {
		logger.debug("Creating Instance");
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setKeyForUpdate(String keyForUpdate) {
		this.keyForUpdate = keyForUpdate;
	}

	public String getColumnFamilyRepresentation(){
		return this.getClass().getName().replace('.', '_');
	}
	
	public boolean isNull() {
		if (null == rowId ||
				"".equals(rowId)){
			return true;
		}
		return false;
	}
	
	
	public String getKeyForUpdate() {
		return keyForUpdate;
	}
	

}
