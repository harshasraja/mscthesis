package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.ColumnFamily;
import harsha.thesis.api.annotation.PrimaryKey;

@ColumnFamily(columnFamily = "Enrolment")
@PrimaryKey(primaryKey = "RowId")
public class Enrolment extends Entity {

    @Column(columnName = "RowId")
    private String rowId;
    @Column(columnName = "StudentId")
    private String studentId;
    @Column(columnName = "CourseId")
    private String courseId;
    @Column(columnName = "Type")
    private String type;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
