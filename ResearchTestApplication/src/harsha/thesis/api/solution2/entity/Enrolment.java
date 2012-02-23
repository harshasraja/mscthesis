package harsha.thesis.api.solution2.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.solution2.entity.Metadata;

@PrimaryKey(primaryKey = "RowId")
public class Enrolment extends BaseEntity implements Cloneable{

    private String rowId;
    @Column(columnName = "UserId")
    private String userId;
    @Column(columnName = "CourseId")
    private String courseId;
    @Column(columnName = "Type")
    private String type;

    public Enrolment() {
        logger.debug("Creating Instance");
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

    @Override
    public boolean isNull() {
        if (null == rowId
                || "".equals(rowId)) {
            return true;
        }
        return false;
    }

    public void setKeyForUpdate(String keyForUpdate) {
        this.keyForUpdate = keyForUpdate;
    }

    public String getColumnFamilyRepresentation() {
        return this.getClass().getName().replace('.', '_');
    }

    @Override
    public String getKeyForUpdate() {
        return super.getKeyForUpdate();
    }

    @Override
    public List<Metadata> getMetaData() {
        return super.getMetaData();
    }

    @Override
    public String getMetadataStringRepresentation() {
        return super.getMetadataStringRepresentation();
    }

    @Override
    public void setMetaData(List<Metadata> metaData) {
        super.setMetaData(metaData);
    }

    @Override
    public void setMetadataStringRepresentation(
            String metadataStringRepresentation)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        super.setMetadataStringRepresentation(metadataStringRepresentation);
    }

    public Object clone() throws CloneNotSupportedException {
        return (Object) super.clone();
    }
}
