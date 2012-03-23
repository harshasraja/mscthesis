/**
 *
 */
package harsha.api.example;

import harsha.api.Entity;
import harsha.api.annotation.Column;
import harsha.api.annotation.ColumnFamily;
import harsha.api.annotation.PrimaryKey;

/**
 * @author harshasraja
 *
 */
@ColumnFamily(columnFamily = "Course")
@PrimaryKey(primaryKey = "CourseId")
public class Course extends Entity {

    @Column(columnName = "CourseId")
    private String courseId;
    @Column(columnName = "CourseName")
    private String courseName;
    @Column(columnName = "Trimester")
    private String trimester;
    @Column(columnName = "Level")
    private String level;
    @Column(columnName = "Year")
    private String year;

    public Course() {
    }

    public Course(String courseId, String courseName, String trimester, String level, String year) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.trimester = trimester;
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

    public String getTrimester() {
        return trimester;
    }

    public void setTrimester(String trimester) {
        this.trimester = trimester;
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
