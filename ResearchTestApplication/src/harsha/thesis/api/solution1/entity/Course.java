/**
 * 
 */
package harsha.thesis.api.solution1.entity;

import java.lang.reflect.InvocationTargetException;

import harsha.api.annotation.Column;
import harsha.api.annotation.PrimaryKey;

/**
 * @author vinay
 *
 */

@PrimaryKey(primaryKey="CourseId")
public class Course extends BaseEntity {
	
	private String courseId;
	
	@Column(columnName="CourseName")
	private String courseName;
	
	@Column(columnName="Trimister")
	private String trimister;
	
	@Column(columnName="Level")
	private String level;
	
	@Column(columnName="Year")
	private String year;

	/**
	 * @throws Exception 
	 * 
	 */
	public Course() throws Exception {
		logger.debug("Creating Instance");
		//{ConstraintName:;KeySpace:;ConstraintType:;TableName:;RKeySpace:;RConstraintName:;RColumn:;DeleteRule:};
		String metadataStringRepresentation = "" +
				"{ConstraintName:CONST200;KeySpace:UNIVERSITY;ConstraintType:P;TableName:harsha_thesis_api_solution1_entity_Course;RKeySpace:UNIVERSITY;RConstraintName:;RColumn:CourseId;DeleteRule:};" +
				"{ConstraintName:CONST600;KeySpace:UNIVERSITY;ConstraintType:F;TableName:harsha_thesis_api_solution1_entity_Enrolment;RKeySpace:UNIVERSITY;RConstraintName:CONST500;RColumn:CourseId;DeleteRule:NODELETE};";
		
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

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTrimister() {
		return trimister;
	}

	public void setTrimister(String trimister) {
		this.trimister = trimister;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public boolean isNull() {
		if (null == courseId ||
				"".equals(courseId)){
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

}
