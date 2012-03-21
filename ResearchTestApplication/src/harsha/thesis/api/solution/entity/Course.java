/**
 *
 */
package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.ColumnFamily;
import harsha.thesis.api.annotation.PrimaryKey;

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

    
    public Course(){
        
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
