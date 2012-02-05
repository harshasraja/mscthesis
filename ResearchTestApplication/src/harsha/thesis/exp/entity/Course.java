/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.entity;

/**
 *
 * @author subramhars
 */
public class Course {

    private String courseId;
    private String courseName;
    private String trimister;
    private String level;
    private String year;

    public Course(String courseId, String courseName, String trimister,
            String level, String year) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.trimister = trimister;
        this.level = level;
        this.year = year;
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
}
