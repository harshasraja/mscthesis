/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

import static harsha.api.test.Experiment.*;
import harsha.thesis.exp.MyMath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author harshasraja
 */
public class Solution {

    public static String[] DEFAULT_COLUMN_ORDER =
            new String[]{
        INSERT_STUDENT, INSERT_COURSE, INSERT_ENROLMENT,
        UPDATE_COURSE, UPDATE_ENROLMENT, UPDATE_STUDENT,
        DELETE_ENROLMENT, DELETE_COURSE, DELETE_STUDENT
    };
    private String code;
    private Map<String, List<Double>> results =
            new HashMap<String, List<Double>>();

    public Solution(String code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void loadFrom(File f, double scale) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(f));
        String line;
        while ((line = r.readLine()) != null) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] keyValue = line.split(":");
            String key = keyValue[0];
            double value = Double.parseDouble(keyValue[1].trim()) * scale;
            List<Double> times = results.get(key);
            if (times == null) {
                times = new ArrayList<Double>();
                results.put(key, times);
            }
            times.add(value);
        }
    }

    public List<Double> getTimesFor(String operation) {
        return results.get(operation);
    }

    public String rToDataFrame(String[] columnOrder) {

        String result = getCode() + ".dataframe = data.frame(\n";
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
