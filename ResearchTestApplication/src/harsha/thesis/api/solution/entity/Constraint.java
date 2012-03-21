/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.ColumnFamily;
import harsha.thesis.api.annotation.PrimaryKey;

/**
 *
 * @author jcrada
 */
@ColumnFamily(columnFamily = "Metadata")
@PrimaryKey(primaryKey = "ConstraintName")
public class Constraint extends Entity {

    @Column(columnName = "ConstraintName")
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

    public Constraint() {
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
    
    public void fromString(String metadata){
        
    }
    
    public String toString(){
        return "";
    }
}
