package harsha.thesis.api.solution1.entity;

import java.lang.reflect.InvocationTargetException;

import harsha.api.annotation.Column;
import harsha.api.annotation.PrimaryKey;

@PrimaryKey(primaryKey="RowId")
public class Enrolment extends BaseEntity implements Cloneable{
	
	private String rowId;
	
	@Column(columnName="UserId")
	private String userId;
	
	@Column(columnName="CourseId")
	private String courseId;
	
	@Column(columnName="Type")
	private String type;

	public Enrolment() throws Exception {
		logger.debug("Creating Instance");
		//{ConstraintName:;KeySpace:;ConstraintType:;TableName:;RKeySpace:;RConstraintName:;RColumn:;DeleteRule:};
		String metadataStringRepresentation = "" +
				"{ConstraintName:CONST300;KeySpace:UNIVERSITY;ConstraintType:P;TableName:harsha_thesis_api_solution1_entity_Enrolment;RKeySpace:UNIVERSITY;RConstraintName:;RColumn:rowId;DeleteRule:};" +
				"{ConstraintName:CONST400;KeySpace:UNIVERSITY;ConstraintType:R;TableName:harsha_thesis_api_solution1_entity_User;RKeySpace:UNIVERSITY;RConstraintName:CONST100;RColumn:UserId;DeleteRule:};" +
				"{ConstraintName:CONST500;KeySpace:UNIVERSITY;ConstraintType:R;TableName:harsha_thesis_api_solution1_entity_Course;RKeySpace:UNIVERSITY;RConstraintName:CONST200;RColumn:CourseId;DeleteRule:};";
		
		try {
			setMetadataStringRepresentation(metadataStringRepresentation);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new Exception(e);
		}
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
	
	@Override
	public boolean isNull() {
		if (null == rowId ||
				"".equals(rowId)){
			return true;
		}
		return false;
	}
	
	public void setKeyForUpdate(String keyForUpdate) {
		this.keyForUpdate = keyForUpdate;
	}
	
	public String getKeyForUpdate() {
		return keyForUpdate;
	}
        
        public Object clone() throws CloneNotSupportedException{
            return (Object) super.clone();
        }

}
