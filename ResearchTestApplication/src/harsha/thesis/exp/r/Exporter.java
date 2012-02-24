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
        String path = "/home/jcrada/Development/hr/ResearchTestApplication/logs/backup/insert-update-delete/";

        String[] filenames = {"Solution0.log", "Solution1.log", "Solution2.log", "Solution3.log", "Solution4.log",};

        Solution[] solutions = new Solution[filenames.length];
        for (int i = 0; i < solutions.length; ++i) {
            solutions[i] = new Solution(filenames[i].substring(0, filenames[i].lastIndexOf(".")));
            solutions[i].loadFrom(new File(path + filenames[i]));
        }


        File out = new File(path + "solutions.R");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();

        BufferedWriter w = new BufferedWriter(new FileWriter(out));
        String[] columnOrder = new String[]{
                    Solution.INSERT_USER, Solution.INSERT_COURSE, Solution.INSERT_ENROLMENT,
                    Solution.UPDATE_ENROLMENT,
                    Solution.DELETE_ENROLMENT, Solution.DELETE_COURSE, Solution.DELETE_USER
                };
        for (Solution s : solutions) {
            w.write(s.rToDataFrame(columnOrder) + "\n");
//            w.write(s.rToDataFrame(Solution.DEFAULT_COLUMN_ORDER) + "\n");
        }


        for (String column : columnOrder) {
            String pngFile = "'" + path + "bp-" + column + ".png'";
            w.write("png(" + pngFile + ", width=640, height=480);\n");
            w.write(Boxplot.ToString(solutions, column) + "\n");
            w.write("dev.off();\n");
            w.flush();
        }


        w.write(Barplot.Definitions());

        for (Solution s : solutions) {
            String pngFile = "'" + path + s.getCode() + "-barplot.png'";
            w.write("png(" + pngFile + ", width=640, height=480);\n");
            w.write(Barplot.ToString(s, columnOrder));
            w.write("dev.off();\n");
        }


//        String pngFile = "'" + path + "barplots.png'";
//        w.write("png(" + pngFile + ", width=640, height=480);\n");
//        w.write(Barplot.ToString(solutions, columnOrder) + "\n");
//        w.write("dev.off();\n");



        w.close();
    }
}
