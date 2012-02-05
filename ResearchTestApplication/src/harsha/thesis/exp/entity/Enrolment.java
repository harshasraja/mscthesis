/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.entity;

/**
 *
 * @author subramhars
 */
public class Enrolment {

    private String rowId;
    private String userId;
    private String courseId;
    private String type;

    public Enrolment(String rowId, String userId, String courseId, String type) {
        this.rowId = rowId;
        this.userId = userId;
        this.courseId = courseId;
        this.type = type;
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
}
