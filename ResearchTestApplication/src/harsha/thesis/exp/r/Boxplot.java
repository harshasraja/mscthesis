/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

import harsha.thesis.exp.MyMath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class Boxplot {
    
    public static String ToString(Solution[] solutions,
            String operation){
        List<String> names = new ArrayList<String>();
        String result = "boxplot(";
        for (int i = 0 ; i < solutions.length; ++i){
            names.add(solutions[i].getCode());
            result += solutions[i].getCode() + ".dataframe$" + operation;
            if (i < solutions.length - 1){
                result += ",";
            }
        }
        result +=",names=" + MyMath.C(names,"'");
        result += ", xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);";
        return result;
    }
    
       
    public static String ThroughputToString(Solution[] solutions,
            String operation){
        List<String> names = new ArrayList<String>();
        String result = "boxplot(";
        for (int i = 0 ; i < solutions.length; ++i){
            names.add(solutions[i].getCode());
            result += numberOfOperations(operation) + " / " +solutions[i].getCode() + ".dataframe$" + operation;
            if (i < solutions.length - 1){
                result += ",";
            }
        }
        result +=",names=" + MyMath.C(names,"'");
        result += ", xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);";
        return result;
    }
    
     public static int numberOfOperations(String table) {
        if (table.contains("student")) {
            return 1000;
        } else if (table.contains("course")) {
            return 1000;
        } else if (table.contains("enrolment")) {
            return 10000;
        }
        throw new RuntimeException("Table " + table + " NOT known");
    }
}
