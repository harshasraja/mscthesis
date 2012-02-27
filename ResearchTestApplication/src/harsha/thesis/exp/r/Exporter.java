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
        String path = "/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r100-sc1000-n10/";
//                "/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000-n10/";

        String[] filenames = {"Solution0.log", "Solution1.log", "Solution2.log", "Solution3.log", "Solution4.log",};

        Solution[] solutions = new Solution[filenames.length];
        for (int i = 0; i < solutions.length; ++i) {
            solutions[i] = new Solution(filenames[i].substring(0, filenames[i].lastIndexOf(".")));
            solutions[i].loadFrom(new File(path + filenames[i]), 1.0 / 1000);
        }

        exportR(solutions, path);
        exportLatex(solutions, path);
    }

    public static void exportR(Solution[] solutions, String path) throws Exception{
        
        File out = new File(path + "solutions.R");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();

        BufferedWriter w = new BufferedWriter(new FileWriter(out));
        String[] columnOrder = new String[]{
            Solution.INSERT_USER, Solution.INSERT_COURSE, Solution.INSERT_ENROLMENT,
            Solution.UPDATE_USER, Solution.UPDATE_COURSE, Solution.UPDATE_ENROLMENT,
            Solution.DELETE_USER, Solution.DELETE_COURSE, Solution.DELETE_ENROLMENT,};
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
            w.flush();
        }


//        String pngFile = "'" + path + "barplots.png'";
//        w.write("png(" + pngFile + ", width=640, height=480);\n");
//        w.write(Barplot.ToString(solutions, columnOrder) + "\n");
//        w.write("dev.off();\n");

        w.close();

    }
    
    public static void exportLatex(Solution[] solutions, String path) throws Exception{
        File out = new File(path + "solutions.tex");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();

        BufferedWriter w = new BufferedWriter(new FileWriter(out));
        String[] columnOrder = new String[]{
            Solution.INSERT_USER, Solution.INSERT_COURSE, Solution.INSERT_ENROLMENT,
            Solution.UPDATE_USER, Solution.UPDATE_COURSE, Solution.UPDATE_ENROLMENT,
            Solution.DELETE_USER, Solution.DELETE_COURSE, Solution.DELETE_ENROLMENT,};
        
        w.write(LatexTable.ToString(solutions, columnOrder));
        
        w.flush();
        w.close();
    }
}
