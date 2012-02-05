/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.connection.CloudConnector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class Main {

    public static final long INSERT_RANDOM_SEED = "insert".hashCode();
    public static final long UPDATE_COURSE_RANDOM_SEED = "update-course".hashCode();
    public static final long UPDATE_ENROLMENT_RANDOM_SEED = "update-enrolment".hashCode();
    public static final long DELETE_RANDOM_SEED = "delete".hashCode();
    public static String HECTOR_CONNECTION = "saddleback:9160@Test Cluster/UNIVERSITY";
    public static String METADATA_CONNECTION = "ambeli:9161@MetadataCluster/Metadata";

    public static String Usage() {
        String usage = "Parameters:\n";
        usage += "\t--solution[0..4]: indicates the solution to run [0:4] \n";
        usage += "\t--log-path[path]: path where the log will be created\n";
        usage += "\t--runs[integer]: number of runs to perform\n";
        usage += "\t--students[integer]: number of students\n";
        usage += "\t--courses[integer]: number of courses\n";
        usage += "\t--csv-base[path]: file to write to the CSV file containing the artificial data\n";
        usage += "\t--create-csv[boolean]: determines whether the CSV file should be created with artificial data\n";
        usage += "\t--initialize[boolean]: prepares and warms up the database\n";

        return usage;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        List<Character> solutions = new ArrayList<Character>();
        int runs = 100, students = 1000, courses = 100, groups = 10;
        String logPath = ".", csvBase = "";
        boolean createCsv = true, initialize = false;

        try {
            for (String arg : args) {
                String value = arg.substring(arg.lastIndexOf("=") + 1);

                if (arg.startsWith("--solution")) {
                    for (char solution : value.toCharArray()) {
                        solutions.add(solution);
                    }
                } else if (arg.startsWith("--log-path")) {
                    logPath = value;
                } else if (arg.startsWith("--runs")) {
                    runs = Integer.parseInt(value);
                } else if (arg.startsWith("--students")) {
                    students = Integer.parseInt(value);
                } else if (arg.startsWith("--courses")) {
                    courses = Integer.parseInt(value);
                } else if (arg.startsWith("--groups")) {
                    groups = Integer.parseInt(value);
                } else if (arg.startsWith("--csv-base")) {
                    csvBase = value;
                } else if (arg.startsWith("--create-csv")) {
                    createCsv = Boolean.parseBoolean(value);
                } else if (arg.startsWith("--initialize")) {
                    initialize = Boolean.parseBoolean(value);
                } else if (arg.startsWith("--?")) {
                    System.out.println(Usage());
                    System.exit(0);
                } else {
                    throw new Exception("Invalid parameter: " + arg + "\n" + Usage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }
        int wait = 3;
        System.out.println("About to start experiment in " + wait + " seconds:");
        System.out.println("\tWorking Directory" + System.getProperty("user.dir"));
        System.out.println("\tSolutions: " + solutions);
        System.out.println("\tLogPath: " + logPath);
        System.out.println("\tRuns: " + runs);
        System.out.println("\tStudents: " + students);
        System.out.println("\tCourses: " + courses);
        System.out.println("\tGroups:" + groups);
        System.out.println("\tCsvBase: " + csvBase);
        System.out.println("\tCreateCsv: " + createCsv);
        System.out.println("\tInitialize: " + initialize);
        for (int i = 0; i < wait; ++i) {
            System.out.print((i + 1) + " ... ");
            Thread.sleep(1000);
        }
        System.out.println("Started...");

        String[] csvFiles = {csvBase + "student.csv", csvBase + "course.csv", csvBase + "enrolment.csv"};
        if (createCsv) {
            ArtificialData ad = new ArtificialData();
            ad.setNumberOfStudents(students);
            ad.setNumberOfCourses(courses);
            ad.setNumberOfGroupsInCourses(groups);
            ad.setHeaderStudent(new String[]{"UserId", "FirstName", "LastName", "Email", "Age", "Type"});
            ad.setHeaderCourse(new String[]{"CourseId", "CourseName", "Trimister", "Level", "Year"});
            ad.setHeaderEnrolment(new String[]{"RowId", "UserId", "CourseId", "Type"});
            for (int i = 0; i < csvFiles.length; ++i) {
                String table = csvFiles[i];
                File csv = new File(table);
                if (csv.exists()) {
                    csv.delete();
                }
                csv.createNewFile();

                BufferedWriter w = new BufferedWriter(new FileWriter(csv));
                switch (i) {
                    case 0:
                        ad.writeStudent(w, ",");
                        break;
                    case 1:
                        ad.writeCourse(w, ",");
                        break;
                    case 2:
                        ad.writeEnrolment(w, ",");
                        break;
                    default:
                        throw new AssertionError();
                }
                w.close();
            }
            System.out.println("CSV files created");
        }

        List<Experiment> experiments = new ArrayList<Experiment>();
        for (Character solution : solutions) {
            Experiment experiment = new Experiment("Solution" + solution, "", logPath);
            experiment.initialize();
            experiments.add(experiment);
        }


        for (Experiment e : experiments) {
            e.log("#\tWorking Directory" + System.getProperty("user.dir") + "\n");
            e.log("#\tSolution: " + e.getCode() + "\n");
            e.log("#\tLogPath: " + logPath + "\n");
            e.log("#\tRuns: " + runs + "\n");
            e.log("#\tStudents: " + students + "\n");
            e.log("#\tCourses: " + courses + "\n");
            e.log("#\tGroups:" + groups + "\n");
            e.log("#\tCsvBase: " + csvBase + "\n");
            e.log("#\tCreateCsv: " + createCsv + "\n");
            e.log("#\tInitialize: " + initialize + "\n");

            char solutionChar = e.getCode().charAt(e.getCode().length() - 1);
            SolutionExperiment solution = null;
            switch (solutionChar) {
                case '0':
                    solution = new Solution0(e, csvFiles);
                    break;
                case '1':
                    solution = new Solution1(e, csvFiles);
                    break;
                case '2':
                    solution = new Solution2(e, csvFiles);
                    break;
                case '3':
                    solution = new Solution3(e, csvFiles);
                    break;
                case '4':
                    solution = new Solution4(e, csvFiles);
                    break;
                default:
                    throw new AssertionError();
            }
            if (initialize) {
                solution.initialize();
            } else {
                solution.experiment(runs);
            }
            e.destroy();
        }
        System.out.println("Total duration: " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("Number of Connections:" + CloudConnector.NUMBER_OF_CONNECTIONS);
    }
}
