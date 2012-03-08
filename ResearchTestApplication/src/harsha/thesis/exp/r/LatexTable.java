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

        String result = "\\begin{table}[h]\n";
        result += "\\newcommand{\\B}[1]{\\colorbox{light-gray}{#1}}\n ";
        result += "\\centering\n\\caption{Average and Standard Deviation}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
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
        result += "\\bottomrule\n";

        result += "\\end{tabular}\n";
        result += "\\end{table}\n";

        return result;
    }
    
      public static String ThroughputToString(Solution[] solutions, String[] operations) {

        String result = "\\begin{table}[h]\n";
        result += "\\newcommand{\\B}[1]{\\colorbox{light-gray}{#1}}\n ";
        result += "\\centering\n\\caption{Throughput}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
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
                    result += DF.format(numberOfOperations(table) / MyMath.Mean(times));
                    if (i < solutions.length - 1) {
                        result += " & ";
                    }
                }
                result += "\\\\\n";
            }
        }
        result += "\\bottomrule\n";

        result += "\\end{tabular}\n";
        result += "\\end{table}\n";

        return result;
    }
      
       public static int numberOfOperations(String table) {
        if (table.contains("user")) {
            return 1000;
        } else if (table.contains("course")) {
            return 1000;
        } else if (table.contains("enrolment")) {
            return 10000;
        }
        throw new RuntimeException("Table " + table + " NOT known");
    }

    public static String RatioToString(Solution[] solutions, String[] operations) {

        String result = "\\begin{table}[h]\n";
        result += "\\newcommand{\\B}[1]{\\colorbox{light-gray}{#1}}\n ";
        result += "\\centering\n\\caption{Ratio}\\label{t:}\n";
        
        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
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
                Solution baseline = solutions[0];
                List<Double> baselineTimes = baseline.getTimesFor(operation + "_" + table);
                result +=  DF.format(MyMath.Mean(baselineTimes)) + " & ";
                for (int i = 1; i < solutions.length; ++i) {
                    Solution s = solutions[i];
                    
                    List<Double> times = s.getTimesFor(operation + "_" + table);
                    result += DF.format(MyMath.Mean(times) / MyMath.Mean(baselineTimes));
                    if (i < solutions.length - 1) {
                        result += " & ";
                    }
                }
                result += "\\\\\n";
            }
        }
        result += "\\bottomrule\n";

        result += "\\end{tabular}\n";
        result += "\\end{table}\n";
        return result;
    }
    
    
     public static String ThroughputRatioToString(Solution[] solutions, String[] operations) {

        String result = "\\begin{table}[h]\n";
        result += "\\newcommand{\\B}[1]{\\colorbox{light-gray}{#1}}\n ";
        result += "\\centering\n\\caption{Throughput Ratio}\\label{t:}\n";
        
        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
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
                Solution baseline = solutions[0];
                List<Double> baselineTimes = baseline.getTimesFor(operation + "_" + table);
                result +=  DF.format(numberOfOperations(table)  / MyMath.Mean(baselineTimes)) + " & ";
                for (int i = 1; i < solutions.length; ++i) {
                    Solution s = solutions[i];
                    
                    List<Double> times = s.getTimesFor(operation + "_" + table);
                    result += DF.format( (numberOfOperations(table) / MyMath.Mean(baselineTimes)) / 
                            (numberOfOperations(table) / MyMath.Mean(times)));
                    if (i < solutions.length - 1) {
                        result += " & ";
                    }
                }
                result += "\\\\\n";
            }
        }
        result += "\\bottomrule\n";

        result += "\\end{tabular}\n";
        result += "\\end{table}\n";
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
