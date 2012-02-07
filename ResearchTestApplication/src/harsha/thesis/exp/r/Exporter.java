/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp.r;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author jcrada
 */
public class Exporter {

    public Exporter() {
    }

    public static void main(String[] args) throws Exception {


        File f0 = new File("./logs/Solution0.log");
        Solution s0 = new Solution("solution0");
        s0.loadFrom(f0);

        File f1 = new File("./logs/Solution1.log");
        Solution s1 = new Solution("solution1");
        s1.loadFrom(f1);

        File f2 = new File("./logs/Solution1.log");
        Solution s2 = new Solution("solution2");
        s1.loadFrom(f2);
        
        File f3 = new File("./logs/Solution1.log");
        Solution s3 = new Solution("solution3");
        s1.loadFrom(f3);
        
        File f4 = new File("./logs/Solution1.log");
        Solution s4 = new Solution("solution4");
        s1.loadFrom(f4);

        File out = new File("/tmp/solutions.R");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();

        BufferedWriter w = new BufferedWriter(new FileWriter(out));
        w.write(s0.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");
        w.write(s1.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");
        w.write(s2.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");
        w.write(s3.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");
        w.write(s4.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");



        for (String column : Solution.DEFAULT_COLUMN_ORDER) {
            String pngFile = "'/tmp/bp-" + column + ".png'";
            w.write("png(" + pngFile + ", width=640, height=480);\n");
            w.write(Boxplot.ToString(new Solution[]{s0, s1, s2, s3, s4}, column) + "\n");
            w.write("dev.off();\n");
            w.flush();
        }

        w.close();
    }
}
