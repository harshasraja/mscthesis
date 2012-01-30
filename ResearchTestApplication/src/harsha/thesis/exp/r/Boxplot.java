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
 * @author jcrada
 */
public class Boxplot {
    
    public static String ToString(Solution[] solutions,
            String operation){
        List<String> names = new ArrayList<String>();
        String result = "boxplot(";
        for (int i = 0 ; i < solutions.length; ++i){
            names.add(solutions[i].getCode());
            result += solutions[i].getCode() + "_df$" + operation;
            if (i < solutions.length - 1){
                result += ",";
            }
        }
        result +=",names=" + MyMath.C(names,"'");
        result += ", xlab='Solutions', ylab='Time (ms)', main='" + operation + "', outline=F);";
        return result;
    }
    
}
