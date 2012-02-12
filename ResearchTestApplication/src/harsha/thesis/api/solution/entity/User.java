/**
 *
 */
package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;

/**
 * @author vinay
 *
 */
public class User extends BaseEntity {

    @Column(columnName = "FirstName")
    private String firstName;
    @Column(columnName = "LastName")
    private String lastName;
    @Column(columnName = "Email")
    private String email;
    @Column(columnName = "Age")
    private String age;
    @Column(columnName = "Type")
    private String type;

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
}
