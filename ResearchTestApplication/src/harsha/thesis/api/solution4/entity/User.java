/**
 * 
 */
package harsha.thesis.api.solution4.entity;

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
	 * 
	 */
	public User() {
		logger.debug("Creating Instance");
	}
	
	public String getKeyForUpdate() {
		return keyForUpdate;
	}



	public void setKeyForUpdate(String keyForUpdate) {
		this.keyForUpdate = keyForUpdate;
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
	
	

}
