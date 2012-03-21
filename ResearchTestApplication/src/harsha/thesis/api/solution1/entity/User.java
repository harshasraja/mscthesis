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
@PrimaryKey(primaryKey="UserId")
public class User extends BaseEntity {
	
	private String userId;
	
	@Column(columnName="FirstName")
	private String firstName;
	
	@Column(columnName="LastName")
	private String lastName;
	
	@Column(columnName="Email")
	private String email;
	
	@Column(columnName="Age")
	private String age;
	
	@Column(columnName="Type")
	private String type;

	/**
	 * @throws Exception 
	 * 
	 */
	public User() throws Exception {
		logger.debug("Creating Instance");
		//{ConstraintName:;KeySpace:;ConstraintType:;TableName:;RKeySpace:;RConstraintName:;RColumn:;DeleteRule:};
		String metadataStringRepresentation = "" +
				"{ConstraintName:CONST100;KeySpace:UNIVERSITY;ConstraintType:P;TableName:harsha_thesis_api_solution1_entity_User;RKeySpace:UNIVERSITY;RConstraintName:;RColumn:UserId;DeleteRule:};" +
				"{ConstraintName:CONST700;KeySpace:UNIVERSITY;ConstraintType:F;TableName:harsha_thesis_api_solution1_entity_Enrolment;RKeySpace:UNIVERSITY;RConstraintName:CONST400;RColumn:UserId;DeleteRule:CASCADE};";
		
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean isNull() {
		if (null == userId ||
				"".equals(userId)){
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
