package harsha.api.example;

import harsha.api.Entity;
import harsha.api.annotation.Column;
import harsha.api.annotation.ColumnFamily;
import harsha.api.annotation.PrimaryKey;

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

    public Enrolment(){
        
    }
    
    public Enrolment(String rowId, String studentId, String courseId, String type){
        this.rowId = rowId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.type = type;
    }
    
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
