package harsha.thesis.api.solution4.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;

@PrimaryKey(primaryKey = "ConstraintName")
public class Metadata extends BaseEntity {

    private String constraintName;
    @Column(columnName = "KeySpace")
    private String keySpace = "";
    @Column(columnName = "ConstraintType")
    private String constraintType = "";
    @Column(columnName = "TableName")
    private String tableName = "";
    @Column(columnName = "RKeySpace")
    private String rKeySpace = "";
    @Column(columnName = "RConstraintName")
    private String rConstraintName = "";
    @Column(columnName = "RColumn")
    private String rColumn = "";
    @Column(columnName = "DeleteRule")
    private String deleteRule = "";

    public Metadata() {
        logger.debug("Creating Instance");
    }

    public Metadata(String constraintName, String keySpace, String constraintType,
            String tableName, String rKeySpace, String rConstraintName, String rColumn,
            String deleteRule) {
        this.constraintName = constraintName;
        this.keySpace = keySpace;
        this.constraintType = constraintType;
        this.tableName = tableName;
        this.rKeySpace = rKeySpace;
        this.rConstraintName = rConstraintName;
        this.rColumn = rColumn;
        this.deleteRule = deleteRule;
    }

    public String getKeyForUpdate() {
        return keyForUpdate;
    }

    public void setKeyForUpdate(String keyForUpdate) {
        this.keyForUpdate = keyForUpdate;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getKeySpace() {
        return keySpace;
    }

    public void setKeySpace(String keySpace) {
        this.keySpace = keySpace;
    }

    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRKeySpace() {
        return rKeySpace;
    }

    public void setRKeySpace(String rKeySpace) {
        this.rKeySpace = rKeySpace;
    }

    public String getRConstraintName() {
        return rConstraintName;
    }

    public void setRConstraintName(String rConstraintName) {
        this.rConstraintName = rConstraintName;
    }

    public String getRColumn() {
        return rColumn;
    }

    public void setRColumn(String rColumn) {
        this.rColumn = rColumn;
    }

    public String getDeleteRule() {
        return deleteRule;
    }

    public void setDeleteRule(String deleteRule) {
        this.deleteRule = deleteRule;
    }

    @Override
    public boolean isNull() {
        if (null == constraintName
                || "".equals(constraintName)) {
            return true;

        }
        return false;
    }
}
