/**
 *
 */
package harsha.thesis.api.solution0.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;

/**
 * @author vinay
 *
 */
@PrimaryKey(primaryKey = "CourseId")
public class Course extends BaseEntity {

    private String courseId;
    @Column(columnName = "CourseName")
    private String courseName;
    @Column(columnName = "Trimister")
    private String trimister;
    @Column(columnName = "Level")
    private String level;
    @Column(columnName = "Year")
    private String year;

    /**
     *
     */
    public Course() {
        logger.debug("Creating Instance");
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

    public void setKeyForUpdate(String keyForUpdate) {
        this.keyForUpdate = keyForUpdate;
    }

    public String getKeyForUpdate() {
        return keyForUpdate;
    }

    public String getColumnFamilyRepresentation() {
        return this.getClass().getName().replace('.', '_');
    }

    @Override
    public boolean isNull() {
        if (null == courseId
                || "".equals(courseId)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Course[id=" + getCourseId()
                + ", name=" + getCourseName() + ", keyForUpdate= " + getKeyForUpdate() + "]";
    }
}
