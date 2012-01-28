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
    
    public static void main(String[] args) {
        System.out.println(MyMath.class.getName());
    }
}
