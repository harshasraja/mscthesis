/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 *
 * @author jcrada
 */
public class Main {

    public static final long INSERT_RANDOM_SEED = "insert".hashCode();
    public static final long UPDATE_RANDOM_SEED = "update".hashCode();
    public static final long DELETE_RANDOM_SEED = "delete".hashCode();
    public static String HECTOR_CONNECTION = "saddleback:9160@Test Cluster/UNIVERSITY";

    public static String Usage() {
        String usage = "Parameters:\n";
        usage += "\t--solution[0..4]: indicates the solution to run [0:4] \n";
        usage += "\t--log-path[path]: path where the log will be created\n";
        usage += "\t--runs[integer]: number of runs to perform\n";
        usage += "\t--students[integer]: number of students\n";
        usage += "\t--courses[integer]: number of courses\n";
        usage += "\t--csv-base[path]: file to write to the CSV file containing the artificial data\n";
        usage += "\t--create-csv[boolean]: determines whether the CSV file should be created with artificial data\n";

        return usage;
    }

    public static void main(String[] args) throws Exception {

        int solution = 0, runs = 5, students = 1000, courses = 100, groups = 10;
        String logPath = ".", csvBase = "";
        boolean createCsv = true;

        try {
            for (String arg : args) {
                String value = arg.substring(arg.lastIndexOf("=") + 1);

                if (arg.startsWith("--solution")) {
                    solution = Integer.parseInt(value);
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
        System.out.println("\tSolution: " + solution);
        System.out.println("\tLogPath: " + logPath);
        System.out.println("\tRuns: " + runs);
        System.out.println("\tStudents: " + students);
        System.out.println("\tCourses: " + courses);
        System.out.println("\tGroups:" + groups);
        System.out.println("\tCsvBase: " + csvBase);
        System.out.println("\tCreateCsv: " + createCsv);
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

        Experiment experiment = new Experiment("Solution" + solution, "", logPath);
        experiment.initialize();

        {
            experiment.log("#\tWorking Directory" + System.getProperty("user.dir") + "\n");
            experiment.log("#\tSolution: " + solution + "\n");
            experiment.log("#\tLogPath: " + logPath + "\n");
            experiment.log("#\tRuns: " + runs + "\n");
            experiment.log("#\tStudents: " + students + "\n");
            experiment.log("#\tCourses: " + courses + "\n");
            experiment.log("#\tGroups:" + groups + "\n");
            experiment.log("#\tCsvBase: " + csvBase + "\n");
            experiment.log("#\tCreateCsv: " + createCsv + "\n");
        }

        switch (solution) {
            case 0:
                new Solution0(experiment, csvFiles).experiment(runs);
                break;
            case 1:
//                Solution1(experiment);
                break;
            case 2:
//                Solution2(experiment);
                break;
            case 3:
//                Solution3(experiment);
                break;
            case 4:
//                Solution4(experiment);
                break;
            default:
                throw new AssertionError();
        }
        experiment.destroy();
    }

    public static void main2(String[] args) throws Exception {
        Experiment e = new Experiment("Solution0", "", "/tmp");
        e.initialize();

        String allArgs = "";
        for (String s : args) {
            allArgs += s + " ";
        }

        e.log("#" + allArgs + "\n\n");

        Pool.Instance().register(e);
        for (int i = 0; i < 50; ++i) {
            test(args);
        }
        e.destroy();
    }

    public static void test(String[] args) throws Exception {
        Random random = new Random();

        Experiment e = Pool.Instance().get("Solution0");



        { //Insert
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.log("Insert: " + (e.duration()) + "\n");
        }

        { //Update
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.log("Update: " + (e.duration()) + "\n");
        }

        {//Delete
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.log("Delete: " + e.duration() + "\n");
        }
        e.log("\n");
    }
}
