/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api;

import harsha.api.Entity;
import harsha.api.annotation.Column;
import harsha.api.annotation.ColumnFamily;
import harsha.api.annotation.PrimaryKey;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author jcrada
 */
@ColumnFamily(columnFamily = "Constraint")
@PrimaryKey(primaryKey = "ConstraintName")
public class Constraint extends Entity {

    @Column(columnName = "ConstraintName")
    private String constraintName;
    @Column(columnName = "KeySpace")
    private String keySpace = "";
    @Column(columnName = "ConstraintType")
    private String constraintType = "";
    @Column(columnName = "ColumnFamily")
    private String columnFamily = "";
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

    public Constraint(String constraintName, String keyspace, String constraintType,
            String columnFamily, String rKeyspace, String rConstraintName, String rColumn,
            String deleteRule) {
        this.constraintName = constraintName;
        this.keySpace = keyspace;
        this.constraintType = constraintType;
        this.columnFamily = columnFamily;
        this.rKeySpace = rKeyspace;
        this.rConstraintName = rConstraintName;
        this.rColumn = rColumn;
        this.deleteRule = deleteRule;
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

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
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

    public static List<Constraint> Parse(String metadata) throws Exception {
        if (metadata == null || metadata.isEmpty()) {
            return new ArrayList<Constraint>();
        }

        List<String> constraints = new ArrayList<String>();

        int openBracket = metadata.indexOf("{");

        while (openBracket != -1) {
            int closeBracket = metadata.indexOf("}");
            if (closeBracket == -1) {
                throw new Exception("Malformed metadata string");
            }
            String constraint = metadata.substring(openBracket, closeBracket + 1);
            constraints.add(constraint);
            metadata = metadata.substring(closeBracket + 1);
            openBracket = metadata.indexOf("{");
        }

        List<Constraint> result = new ArrayList<Constraint>();

        for (String constraint : constraints) {
            Constraint entity = new Constraint();
            StringTokenizer tokenizer = new StringTokenizer(constraint, ";");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().replaceAll("\\{|\\}", "");
                String[] keyValue = token.split(":");
                if (keyValue.length == 1) {
                    Entity.SetValue(keyValue[0], "", entity);
                } else if (keyValue.length == 2) {
                    Entity.SetValue(keyValue[0], keyValue[1], entity);
                } else {
                    throw new Exception(
                            "Error parsing token [" + token + "] for constraint ["
                            + constraint + "] in metadata [" + metadata);
                }
            }
            result.add(entity);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        String constraints =
                "{ConstraintName:CONST200;KeySpace:UNIVERSITY;ConstraintType:P;"
                + "ColumnFamily:harsha.thesis.api.solution2.entity.Course;"
                + "RKeySpace:UNIVERSITY;RConstraintName:;RColumn:CourseId;DeleteRule:};"
                + ""
                + "{ConstraintName:CONST600;KeySpace:UNIVERSITY;ConstraintType:F;"
                + "ColumnFamily:harsha_thesis_api_solution2_entity_Enrolment;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST500;RColumn:CourseId;"
                + "DeleteRule:NODELETE};"
                + ""
                + "{ConstraintName:CONST200;KeySpace:UNIVERSITY;ConstraintType:P;"
                + "ColumnFamily:harsha_thesis_api_solution2_entity_Course;"
                + "RKeySpace:UNIVERSITY;RConstraintName:;RColumn:CourseId;DeleteRule:};"
                + ""
                + "{ConstraintName:CONST600;KeySpace:UNIVERSITY;ConstraintType:F;"
                + "ColumnFamily:harsha_thesis_api_solution2_entity_Enrolment;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST500;RColumn:CourseId;"
                + "DeleteRule:NODELETE}";

        List<Constraint> metadata = Parse(constraints);
        System.out.println("Number of constraints:" + metadata.size());
        for (Constraint constraint : metadata) {
            System.out.println(constraint);
        }


    }
}
