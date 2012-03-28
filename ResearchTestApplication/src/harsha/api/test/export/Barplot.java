/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test.export;

import harsha.thesis.exp.MyMath;
import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author harshasraja
 */
public class Barplot {

    public static final String BAR_WIDTH = "def.barplot.width";
    public static final String BAR_SEP_WIDTH = "def.barplot.sepwidth";
    public static final String BAR_SPACE = "def.barplot.space";
    public static final String BAR_GROUP_SPACE = "def.barplot.groupspace";

    public static String Definitions() {
        String def = BAR_WIDTH + "=1; #bar width\n"
                + BAR_SEP_WIDTH + "=0; #separation bar width\n"
                + BAR_SPACE + "=0; #space between bars\n"
                + BAR_GROUP_SPACE + "=1; #space between groups\n";
        return def;
    }

    public static String Par() {
        return "par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);\n";
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

    public static String ResponseTimeToString(Solution solution, String[] operations) {

        Map<String, List<String>> map = groupOperations(operations);
        Set<String> crud = map.keySet();



        String dataframe = "data.frame(";
        List<String> tableNames = new ArrayList<String>();
        for (Iterator<String> it = crud.iterator(); it.hasNext();) {
            String op = it.next();
            dataframe += op + "=";
            List<String> means = new ArrayList<String>();
            List<String> tables = map.get(op); //User Course Enrolment
            for (String table : tables) {
                means.add("mean(" + solution.getCode() + ".dataframe$"
                        + op + "_" + table + ")/" + numberOfOperations(table));

                tableNames.add("" + table.charAt(0));
            }
            dataframe += MyMath.C(means);
            if (it.hasNext()) {
                dataframe += ",\n";
            } else {
                dataframe += ")";
            }
        }

        String result = "";
        result += solution.getCode() + ".barplot.means=" + dataframe + ";\n";
        result += "barplot(as.matrix(" + solution.getCode() + ".barplot.means),"
                + "beside=T, names=" + MyMath.C(tableNames, "'") + ", ylab='Milliseconds per entity', "
                + "xlab = '');\n";

        float start = 2.5f, by = 4.0f;
        for (Iterator<String> it = crud.iterator(); it.hasNext();) {
            result += "mtext('" + it.next() + "', side=1, line=2, at=" + start + ");\n";
            start += by;
        }

        return result;
    }

    public static String ThroughputToString(Solution solution, String[] operations) {

        Map<String, List<String>> map = groupOperations(operations);
        Set<String> crud = map.keySet();


        String dataframe = "data.frame(";
        List<String> tableNames = new ArrayList<String>();
        for (Iterator<String> it = crud.iterator(); it.hasNext();) {
            String op = it.next();
            dataframe += op + "=";
            List<String> means = new ArrayList<String>();
            List<String> tables = map.get(op); //User Course Enrolment
            for (String table : tables) {
                means.add((numberOfOperations(table) * 1000) + "/ mean(" + solution.getCode() + ".dataframe$"
                        + op + "_" + table + ")");

                tableNames.add("" + table.charAt(0));
            }
            dataframe += MyMath.C(means);
            if (it.hasNext()) {
                dataframe += ",\n";
            } else {
                dataframe += ")";
            }
        }

        String result = "";
        result += solution.getCode() + ".barplot.means=" + dataframe + ";\n";
        result += "barplot(as.matrix(" + solution.getCode() + ".barplot.means),"
                + "beside=T, names=" + MyMath.C(tableNames, "'") + ", ylab='Entities per second', "
                + "xlab = '');\n";

        float start = 2.5f, by = 4.0f;
        for (Iterator<String> it = crud.iterator(); it.hasNext();) {
            result += "mtext('" + it.next() + "', side=1, line=2, at=" + start + ");\n";
            start += by;
        }

        return result;
    }

    public static String ResponseTimeToString(String[] operations, Solution[] solutions) {
        String code = operations[0].split("_")[0];
        List<String> names = new ArrayList<String>();
        String dataframe = "data.frame(";



        for (int i = 0; i < solutions.length; ++i) {
            Solution solution = solutions[i];
            List<String> means = new ArrayList<String>();
            for (int j = 0; j < operations.length; ++j) {
                String operation = operations[j];
                means.add("mean(" + solution.getCode() + ".dataframe$" + operation + ") / "
                        + numberOfOperations(operation));
                String tableChar = "" + operation.split("_")[1].charAt(0);
                names.add(tableChar);
            }
            dataframe += solution.getCode() + " = " + MyMath.C(means);
            if (i < solutions.length - 1) {
                dataframe += ", ";
            } else {
                dataframe += ")";
            }
        }

        String result = "";
        result += code + ".barplot.means=" + dataframe + ";\n";
        result += "barplot(as.matrix(" + code + ".barplot.means),"
                + "beside=T, names=" + MyMath.C(names, "'") + ", ylab='Milliseconds per entity', xlab='');\n";

//        Map<String, List<String>> keyValue = groupOperations(operations);


        float start = 2.5f, by = 4.0f;
        for (Solution solution : solutions) {
            result += "mtext('" + solution.getCode() + "', side=1, line=2, at=" + start + ");\n";
            start += by;
        }

        return result;
    }

    public static String ResponseTimeToString(String operation, Solution[] solutions) {
        String result = "barplot(";

        List<String > mean = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for (Solution s : solutions) {
            mean.add("mean(" + MyMath.C(s.getTimesFor(operation)) + ") / " + numberOfOperations(operation) );
            names.add(s.getCode());
        }
        result += MyMath.C(mean) + ", names=" + MyMath.C(names, "'")
                + ", col=gray.colors(" + solutions.length + "), xlab='', ylab='Milliseconds per entity'";
        result += ");";

        return result;
    }

    public static String ThoughputToString(String operation, Solution[] solutions) {
        String result = "barplot(";

        List<String> mean = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for (Solution s : solutions) {
            mean.add((1000 * numberOfOperations(operation)) + "/ mean(" + MyMath.C(s.getTimesFor(operation)) + ")");
            names.add(s.getCode());
        }
        result += MyMath.C(mean) + ", names=" + MyMath.C(names, "'")
                + ", col=gray.colors(" + solutions.length + "), xlab='', ylab='Entities per second'";
        result += ");";

        return result;
    }

    public static String ThroughputToString(String[] operations, Solution[] solutions) {
        String code = operations[0].split("_")[0];
        List<String> names = new ArrayList<String>();
        String dataframe = "data.frame(";




        for (int i = 0; i < solutions.length; ++i) {
            Solution solution = solutions[i];
            List<String> means = new ArrayList<String>();
            for (int j = 0; j < operations.length; ++j) {
                String operation = operations[j];
                means.add((1000 * numberOfOperations(operation)) + " / mean(" + solution.getCode() + ".dataframe$" + operation + ")");
                String tableChar = "" + operation.split("_")[1].charAt(0);
                names.add(tableChar);
            }
            dataframe += solution.getCode() + " = " + MyMath.C(means);
            if (i < solutions.length - 1) {
                dataframe += ", ";
            } else {
                dataframe += ")";
            }
        }

        String result = "";
        result += code + ".barplot.means=" + dataframe + ";\n";
        result += "barplot(as.matrix(" + code + ".barplot.means),"
                + "beside=T, names=" + MyMath.C(names, "'") + ", ylab='Entities per second', xlab='');\n";

//        Map<String, List<String>> keyValue = groupOperations(operations);


        float start = 2.5f, by = 4.0f;
        for (Solution solution : solutions) {
            result += "mtext('" + solution.getCode() + "', side=1, line=2, at=" + start + ");\n";
            start += by;
        }

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
}
