/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

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
 * @author jcrada
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

//    public static String ToString(Solution[] solutions, String[] operations) {
//        List<String> widths = new ArrayList<String>();
//        List<String> spaces = new ArrayList<String>();
//
//        String result = "";
//        for (Solution solution : solutions) {
//            List<String> means = new ArrayList<String>();
//            List<String> stdevs = new ArrayList<String>();
//            widths.add("rep(" + BAR_WIDTH + ", " + solutions.length + ")");
//            widths.add(BAR_SEP_WIDTH);
//
//            spaces.add("rep(" + BAR_SPACE + "," + solutions.length + ")");
//            spaces.add(BAR_GROUP_SPACE);
//
//            for (String operation : operations) {
//                means.add("mean(" + solution.getCode() + ".dataframe$" + operation + ")");
//                stdevs.add("sd(" + solution.getCode() + ".dataframe$" + operation + ")");
//            }
//            result += solution.getCode() + ".means=" + MyMath.C(means) + ";\n";
//            result += solution.getCode() + ".stdevs=" + MyMath.C(stdevs) + ";\n";
//            result += solution.getCode() + ".means.names=" + MyMath.C(operations, "'") + ";\n";
//            result += solution.getCode() + ".stdevs.names=" + MyMath.C(operations, "'") + ";\n";
//        }
//
//        List<String> means = new ArrayList<String>();
//        List<String> names = new ArrayList<String>();
//        for (int i = 0; i < solutions.length; ++i) {
//            means.add(solutions[i].getCode() + ".means");
//            names.add(solutions[i].getCode() + ".means.names");
//            if (i < solutions.length - 1) {
//                means.add("0");
//                names.add("''");
//            }
//        }
//        result += "my.barplot.means=" + MyMath.C(means) + ";\n";
//        result += "my.barplot.widths=" + MyMath.C(widths) + ";\n";
//        result += "my.barplot.spaces=" + MyMath.C(spaces) + ";\n";
//        result += "my.barplot.names=" + MyMath.C(names) + ";\n";
//        result += "barplot(my.barplot.means, width=my.barplot.widths, "
//                + "space=my.barplot.spaces, names=my.barplot.names);\n";
//
//        return result;
//    }

    
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

//    public static String ToString(Solution solution, String[] operations) {
//
//        Map<String, List<String>> map = groupOperations(operations);
//        Set<String> crud = map.keySet();
//
//        List<String> means = new ArrayList<String>();
//        List<String> widths = new ArrayList<String>();
//        List<String> spaces = new ArrayList<String>();
//        List<String> tableNames = new ArrayList<String>();
////        for (String op : crud) {
//        for (Iterator<String> it = crud.iterator(); it.hasNext();) {
//            String op = it.next();
//            List<String> tables = map.get(op);
//            for (String table : tables) {
//                means.add("mean(" + solution.getCode() + ".dataframe$"
//                        + op + "_" + table + ")");
//                tableNames.add("'" + table.charAt(0) + "'");
//            }
//            if (it.hasNext()) {
//                means.add("0");
//                tableNames.add("''");
//                widths.add("rep(" + BAR_WIDTH + ", " + tables.size() + ")");
//                widths.add(BAR_SEP_WIDTH);
//
//                spaces.add("rep(" + BAR_SPACE + "," + tables.size() + ")");
//                spaces.add(BAR_GROUP_SPACE);
//            }
//
//        }
//        String result = "";
//        result += solution.getCode() + ".barplot.means=" + MyMath.C(means) + ";\n";
//        result += solution.getCode() + ".barplot.widths=" + MyMath.C(widths) + ";\n";
//        result += solution.getCode() + ".barplot.spaces=" + MyMath.C(spaces) + ";\n";
//        result += solution.getCode() + ".barplot.names=" + MyMath.C(tableNames) + ";\n";
//        result += "barplot(" + solution.getCode() + ".barplot.means, "
//                + "width=" + solution.getCode() + ".barplot.widths, "
//                + "space=" + solution.getCode() + ".barplot.spaces, "
//                + "names=" + solution.getCode() + ".barplot.names,"
//                + "xlab=" + MyMath.Paste(new ArrayList<String>(crud),"'", " ") + ");\n";
//
//        return result;
//    }
    
    
        public static String ToString(Solution solution, String[] operations) {

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
                        + op + "_" + table + ")");
                tableNames.add(table.charAt(0) + "");
            }
            dataframe += MyMath.C(means);
            if (it.hasNext()){
                dataframe += ",\n";
            }else dataframe += ")";
        }
        
        String result = "";
        result += solution.getCode() + ".barplot.means=" + dataframe + ";\n";
        result += "barplot(as.matrix(" + solution.getCode() + ".barplot.means),"
                + "beside=T, names=" + MyMath.C(tableNames,"'") + ", ylab='Time (s)');\n";
        
        float start = 2.5f, by = 4.0f;
        for (Iterator<String> it = crud.iterator() ; it.hasNext();){
            result += "mtext('" + it.next() + "', side=1, line=2, at=" + start +");\n";
            start += by;
        }

        return result;
    }
}
