/**
 *
 */
package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;

/**
 * @author vinay
 *
 */
public class Course extends BaseEntity {

    @Column(columnName = "CourseName")
    private String courseName;
    @Column(columnName = "Trimister")
    private String trimister;
    @Column(columnName = "Level")
    private String level;
    @Column(columnName = "Year")
    private String year;

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
