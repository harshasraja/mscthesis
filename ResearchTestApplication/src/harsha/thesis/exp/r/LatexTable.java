/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

import harsha.thesis.exp.MyMath;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author jcrada
 */
public class LatexTable {

    private static final DecimalFormat DF = new DecimalFormat("0.000");

    public static String ToString(Solution[] solutions, String[] operations) {

        String result = "%\\newcommand{\\B}[1]{\\colorbox{light-gray}{#1}} % ";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n";
        for (int i = 0; i < solutions.length; ++i) {
            Solution s = solutions[i];
            result += s.getCode();
            if (i < solutions.length - 1) {
                result += " & ";
            } else {
                result += "\\\\\n";
            }
        }

        Map<String, List<String>> crud = groupOperations(operations);

        Set<String> crudKeys = crud.keySet();
        for (String operation : crudKeys) {
            result += "\\midrule\n";
            result += "\\multirow{" + crudKeys.size() + "}{*}{" + operation + "}";
            for (String table : crud.get(operation)) {
                result += " & " + table.charAt(0) + " & ";
                for (int i = 0; i < solutions.length; ++i) {
                    Solution s = solutions[i];
                    List<Double> times = s.getTimesFor(operation + "_" + table);
                    result += DF.format(MyMath.Mean(times)) + " ("
                            + DF.format(MyMath.StDev(times)) + ")";
                    if (i < solutions.length - 1) {
                        result += " & ";
                    }
                }
                result += "\\\\\n";
            }
        }
        result += "\\bottomrule"

        result += "\\end{tabular}\n";

        return result;
    }

    private static Map<String, List<String>> groupOperations(String[] operations) {
        Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
        for (String operation : operations) {
            String[] split = operation.split("_");
            List<String> entities = result.get(split[0]);
            if (entities == null) {
                entities = new ArrayList<String>();
                result.put(split[0], entities);
            }
            entities.add(split[1]);
        }
        return result;
    }
}
