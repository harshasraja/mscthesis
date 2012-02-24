/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import java.util.ArrayList;
import java.util.List;
import org.apache.cassandra.utils.Pair;

/**
 *
 * @author jcrada
 */
public class MyMath {

    public static List<Pair<Double, Double>> Overlap(
            double min, double max, int number_of_sections) {
        List<Pair<Double, Double>> result = new ArrayList<Pair<Double, Double>>();

        double range = (max - min) / (0.5 * (number_of_sections + 1));
        double current_step = min + 0.5 * range;
        for (int i = 0; i < number_of_sections; ++i) {
            double from = current_step - 0.5 * range;
            double to = current_step + 0.5 * range;
            result.add(new Pair<Double, Double>(from, to));
            current_step += 0.5 * range;
        }
        return result;
    }

    public static String C(List<?> x, String begin_enclosing, String end_enclosing) {
        String c = "c(";
        for (int i = 0; i < x.size(); ++i) {
            c += begin_enclosing + x.get(i).toString() + end_enclosing;
            if (i < x.size() - 1) {
                c += ", ";
            }
        }
        c += ")";
        return c;
    }

    public static String C(List<?> x, String enclosing) {
        return C(x, enclosing, enclosing);
    }

    public static String C(List<?> x) {
        return C(x, "");
    }

    public static String C(Object[] x, String begin_enclosing, String end_enclosing) {
        String c = "c(";
        for (int i = 0; i < x.length; ++i) {
            c += begin_enclosing + x[i].toString() + end_enclosing;
            if (i < x.length - 1) {
                c += ", ";
            }
        }
        c += ")";
        return c;
    }

    public static String Paste(List<?> items, String begin_enclosing, String end_enclosing, String sep){
        String result = "paste(";
        for (Object item : items){
            result+= begin_enclosing + item + end_enclosing + ",";
        }
        result += " sep='" + sep +"')";
        return result;
    }
    
    public static String Paste(List<?> items, String enclosing, String sep){
        return Paste(items, enclosing, enclosing, sep);
    }
    
    public static String C(Object[] x, String enclosing) {
        return C(x, enclosing, enclosing);
    }

    public static String C(Object[] x) {
        return C(x, "");
    }

    public static void main(String[] args) {
        System.out.println(MyMath.class.getName());
    }
}
