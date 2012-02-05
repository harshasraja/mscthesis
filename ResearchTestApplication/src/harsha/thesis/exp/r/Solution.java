/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

import harsha.thesis.exp.MyMath;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jcrada
 */
public class Solution {

    public static final String INSERT_USER = "insert_user";
    public static final String INSERT_COURSE = "insert_course";
    public static final String INSERT_ENROLMENT = "insert_enrolment";
    public static final String UPDATE_COURSE = "update_course";
    public static final String UPDATE_ENROLMENT = "update_enrolment";
    public static final String DELETE_ENROLMENT = "delete_enrolment";
    public static final String DELETE_COURSE = "delete_course";
    public static final String DELETE_USER = "delete_user";
    public static String[] DEFAULT_COLUMN_ORDER =
            new String[]{
        INSERT_USER, INSERT_COURSE, INSERT_ENROLMENT,
        UPDATE_COURSE, UPDATE_ENROLMENT,
        DELETE_ENROLMENT, DELETE_COURSE, DELETE_USER
    };
    private String code;
    private Map<String, List<Integer>> results =
            new HashMap<String, List<Integer>>();

    public Solution(String code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void loadFrom(File f) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(f));
        String line;
        while ((line = r.readLine()) != null) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] keyValue = line.split(":");
            String key = keyValue[0];
            int value = Integer.parseInt(keyValue[1].trim());
            List<Integer> times = results.get(key);
            if (times == null) {
                times = new ArrayList<Integer>();
                results.put(key, times);
            }
            times.add(value);
        }
    }

    public List<Integer> getTimesFor(String operation) {
        return results.get(operation);
    }

    public String rToDataFrame(String[] columnOrder) {

        String result = getCode() + "_df = data.frame(\n";
        for (int i = 0; i < columnOrder.length; ++i) {
            String column = columnOrder[i];
            result += column + "=" + MyMath.C(getTimesFor(column));
            if (i < columnOrder.length - 1) {
                result += ",\n";
            }
        }
        result += ");\n";
        return result;
    }

    public String rToString() {
        String result = "";
        for (String column : DEFAULT_COLUMN_ORDER) {
            result += column + " = " + MyMath.C(getTimesFor(column)) + "\n";
        }
        return result;
    }


}
