package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;

public class Enrolment extends Entity {

    @Column(columnName = "UserId")
    private String userId;
    @Column(columnName = "CourseId")
    private String courseId;
    @Column(columnName = "Type")
    private String type;

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
