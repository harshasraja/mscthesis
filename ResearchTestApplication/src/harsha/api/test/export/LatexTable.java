/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test.export;

import harsha.thesis.exp.MyMath;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author harshasraja
 */
public class LatexTable {

    private static final DecimalFormat DF = new DecimalFormat("0.000");

    public static String ResponseTimeToString(Solution[] solutions, String[] operations) {

        String result = "\\begin{table}[h]\n";
        result += "\\centering\n\\caption{Response time in milliseconds per entity}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
        for (int i = 0; i < solutions.length; ++i) {
            Solution s = solutions[i];
            result += "\\textbf{" + s.getCode() + "}";
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
            result += "\\multirow{" + crudKeys.size() + "}{*}{\\textbf{" + operation + "}}";
            for (String table : crud.get(operation)) {
                String tableChar = "" + table.charAt(0);
                tableChar = "\\textbf{" + tableChar + "}";
                result += " & " + tableChar + " & ";
                for (int i = 0; i < solutions.length; ++i) {
                    Solution s = solutions[i];
                    List<Double> times = new ArrayList<Double>(s.getTimesFor(operation + "_" + table));
                    MyMath.Divide(times, numberOfOperations(table));
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
        DecimalFormat DF = new DecimalFormat("0");
        String result = "\\begin{table}[h]\n";
        result += "\\centering\n\\caption{Throughput in entities per second}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
        for (int i = 0; i < solutions.length; ++i) {
            Solution s = solutions[i];
            result += "\\textbf{" + s.getCode() + "}";
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
            result += "\\multirow{" + crudKeys.size() + "}{*}{\\textbf{" + operation + "}}";
            for (String table : crud.get(operation)) {
                String tableChar = "" + table.charAt(0);

                tableChar = "\\textbf{" + tableChar + "}";
                result += " & " + tableChar + " & ";
                for (int i = 0; i < solutions.length; ++i) {
                    Solution s = solutions[i];
                    List<Double> throughput = new ArrayList<Double>();
                    for (Double x : s.getTimesFor(operation + "_" + table)) {
                        throughput.add(1000 * numberOfOperations(table) / x);
                    }
                    result += DF.format(MyMath.Mean(throughput))
                            + " (" + DF.format(MyMath.StDev(throughput)) + ")";
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
        if (table.contains("student")) {
            return 1000;
        } else {
            if (table.contains("course")) {
                return 1000;
            } else {
                if (table.contains("enrolment")) {
                    return 10000;
                }
            }
        }
        throw new RuntimeException("Table " + table + " NOT known");
    }

    public static String RatioToString(Solution[] solutions, String[] operations) {
        DecimalFormat DF = new DecimalFormat("0.00");
        String result = "\\begin{table}[h]\n";
        result += "\\centering\n\\caption{Response time ratio}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
        for (int i = 0; i < solutions.length; ++i) {
            Solution s = solutions[i];
            result += "\\textbf{" + s.getCode() + "}";
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
            result += "\\multirow{" + crudKeys.size() + "}{*}{\\textbf{" + operation + "}}";
            for (String table : crud.get(operation)) {
                String tableChar = "" + table.charAt(0);

                result += " & \\textbf{" + tableChar + "} & ";
                Solution baseline = solutions[0];
                List<Double> baselineTimes = new ArrayList<Double>(baseline.getTimesFor(operation + "_" + table));
                MyMath.Divide(baselineTimes, numberOfOperations(table));

                result += DF.format(MyMath.Mean(baselineTimes)) + " & ";
                for (int i = 1; i < solutions.length; ++i) {
                    Solution s = solutions[i];

                    List<Double> times = new ArrayList<Double>(s.getTimesFor(operation + "_" + table));
                    MyMath.Divide(times, numberOfOperations(table));
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
        DecimalFormat DF = new DecimalFormat("0.00");
        DecimalFormat DFB = new DecimalFormat("0");
        String result = "\\begin{table}[h]\n";
        result += "\\centering\n\\caption{Throughput inverse ratio}\\label{t:}\n";

        result += "\\begin{tabular}{" + MyMath.Repeat("c", solutions.length + 2) + "}\n";

        //header
        result += "\\toprule\n&&";
        for (int i = 0; i < solutions.length; ++i) {
            Solution s = solutions[i];
            result += "\\textbf{" + s.getCode() + "}";
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
            result += "\\multirow{" + crudKeys.size() + "}{*}{\\textbf{" + operation + "}}";
            for (String table : crud.get(operation)) {
                String tableChar = "" + table.charAt(0);

                result += " & \\textbf{" + tableChar + "} & ";
                Solution baseline = solutions[0];
                List<Double> baselineTimes = baseline.getTimesFor(operation + "_" + table);
                List<Double> baselineThrouhput = new ArrayList<Double>();
                for (Double x : baselineTimes) {
                    baselineThrouhput.add(numberOfOperations(table) / x);
                }

                result += DFB.format(MyMath.Mean(baselineThrouhput)) + " & ";
                for (int i = 1; i < solutions.length; ++i) {
                    Solution s = solutions[i];

                    List<Double> times = s.getTimesFor(operation + "_" + table);
                    List<Double> throuhput = new ArrayList<Double>();
                    for (Double x : times) {
                        throuhput.add(numberOfOperations(table) / x);
                    }

                    result += DF.format(MyMath.Mean(baselineThrouhput) / MyMath.Mean(throuhput));

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
