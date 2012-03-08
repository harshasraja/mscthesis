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

    public static String Definitions() {
        return "par(las=1);\n";
    }

    public static void main(String[] args) throws Exception {
        String path =
                "/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r100-sc1000-n10-final/";
//                "/u/students/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000-n10-final/";

        String[] filenames = {"Solution0.log", "Solution1.log", "Solution2.log", "Solution3.log", "Solution4.log",};

        Solution[] solutions = new Solution[filenames.length];
        for (int i = 0; i < solutions.length; ++i) {
            solutions[i] = new Solution(filenames[i].substring(0, filenames[i].lastIndexOf(".")));
            solutions[i].loadFrom(new File(path + filenames[i]), 1.0 / 1000);
        }

        exportR(solutions, path);
        exportLatex(solutions, path);
    }

    public static void exportR(Solution[] solutions, String path) throws Exception {

        File out = new File(path + "solutions.R");
        if (out.exists()) {
            out.delete();
        }
        out.createNewFile();

        BufferedWriter w = new BufferedWriter(new FileWriter(out));

        w.write(Definitions() + "\n");

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
            w.write(Definitions() + "\n");
            w.write(Boxplot.ToString(solutions, column) + "\n");
            w.write("dev.off();\n");
            w.flush();
        }


        w.write(Barplot.Definitions());

        for (Solution s : solutions) {
            String pngFile = "'" + path + s.getCode() + "-barplot.png'";
            w.write("png(" + pngFile + ", width=640, height=480);\n");
            w.write(Definitions() + "\n");
            w.write(Barplot.ToString(s, columnOrder));
            w.write("dev.off();\n");
            w.flush();
        }

        String[][] setOfOperations = {
            {Solution.INSERT_USER, Solution.INSERT_COURSE, Solution.INSERT_ENROLMENT},
            {Solution.UPDATE_USER, Solution.UPDATE_COURSE, Solution.UPDATE_ENROLMENT},
            {Solution.DELETE_USER, Solution.DELETE_COURSE, Solution.DELETE_ENROLMENT},};

        for (String[] operations : setOfOperations) {
            String filename = "'" + path + "op-" + operations[0].split("_")[0] + "-barplot.png'";
            w.write("png(" + filename + ", width=640, height=480);\n");
            w.write(Definitions() + "\n");
            w.write(Barplot.ToString(operations, solutions) + "\n");
            w.write("dev.off();\n\n");
            
            filename = "'" + path + "th-" + operations[0].split("_")[0] + "-barplot.png'";
            w.write("png(" + filename + ", width=640, height=480);\n");
            w.write(Definitions() + "\n");
            w.write(Barplot.ThroughputToString(operations, solutions) + "\n");
            w.write("dev.off();\n");
        }

//        String pngFile = "'" + path + "barplots.png'";
//        w.write("png(" + pngFile + ", width=640, height=480);\n");
//        w.write(Barplot.ToString(solutions, columnOrder) + "\n");
//        w.write("dev.off();\n");

        w.close();

    }

    public static void exportLatex(Solution[] solutions, String path) throws Exception {
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

        w.write(LatexTable.ToString(solutions, columnOrder) + "\n\n\n");
        w.write(LatexTable.RatioToString(solutions, columnOrder) + "\n\n\n\n\n\n");
        
        w.write(LatexTable.ThroughputToString(solutions, columnOrder) + "\n\n\n");
//        w.write(LatexTable.ThroughputRatioToString(solutions, columnOrder) + "\n\n\n\n\n\n");
        w.flush();
        w.close();
    }
}
