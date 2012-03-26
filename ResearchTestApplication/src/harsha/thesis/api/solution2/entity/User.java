/**
 * 
 */
package harsha.thesis.api.solution2.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import harsha.api.annotation.Column;
import harsha.api.annotation.PrimaryKey;
import harsha.thesis.api.solution2.entity.Metadata;

/**
 * @author harshasraja
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

	public String getColumnFamilyRepresentation(){
		return this.getClass().getName().replace('.', '_');
	}

	@Override
	public String getKeyForUpdate() {
		return super.getKeyForUpdate();
	}

	@Override
	public List<Metadata> getMetaData() {
		return super.getMetaData();
	}

	@Override
	public String getMetadataStringRepresentation() {
		return super.getMetadataStringRepresentation();
	}

	@Override
	public void setMetaData(List<Metadata> metaData) {
		super.setMetaData(metaData);
	}

	@Override
	public void setMetadataStringRepresentation(
			String metadataStringRepresentation)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		// TODO Auto-generated method stub
		super.setMetadataStringRepresentation(metadataStringRepresentation);
	}
	
	
	

}
