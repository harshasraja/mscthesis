/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import harsha.api.Constraint;
import harsha.api.EntityManager;
import harsha.api.connection.CloudConnector;
import harsha.api.solutions.em.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author jcrada
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class);
    public static String HECTOR_CONNECTION = "saddleback:9160@Test Cluster/UNIVERSITY";
    public static String METADATA_CONNECTION = "ambeli:9161@MetadataCluster/METADATA";

    public static String Usage() {
        String usage = "Parameters:\n";
        usage += "\t--solution[0..4]: indicates the solution to run [0:4] \n";
        usage += "\t--log-path[path]: path where the log will be created\n";
        usage += "\t--runs[integer]: number of runs to perform\n";
        usage += "\t--students[integer]: number of students\n";
        usage += "\t--courses[integer]: number of courses\n";
        usage += "\t--courses-per-student[integer]: number of courses per student\n";
        usage += "\t--initialize[boolean]: prepares the keyspace\n";
        usage += "\t--metadata-csv[filepath]: file path to CSV containing metadata\n";
        return usage;
    }

    public static void main(String[] args) throws Exception {
        Logger.getRootLogger().setLevel(Level.INFO);
        long startTime = System.currentTimeMillis();
        List<Character> solutions = new ArrayList<Character>();
        int runs = 100, students = 1000, courses = 1000, coursesPerStudent = 10;
        String logPath = ".", metadata = "";
        boolean initialize = false;

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
                } else if (arg.startsWith("--courses-per-student")) {
                    coursesPerStudent = Integer.parseInt(value);
                } else if (arg.startsWith("--courses")) {
                    courses = Integer.parseInt(value);
                } else if (arg.startsWith("--initialize")) {
                    initialize = Boolean.parseBoolean(value);
                } else if (arg.startsWith("--metadata-csv")) {
                    File metadataFile = new File(value);
                    metadata = Constraint.ToString(
                            Constraint.ParseFromCsv(metadataFile));
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
        System.out.println("#About to start experiment in " + wait + " seconds:");
        System.out.println("#\tWorking Directory" + System.getProperty("user.dir"));
        System.out.println("#\tSolutions: " + solutions);
        System.out.println("#\tLogPath: " + logPath);
        System.out.println("#\tRuns: " + runs);
        System.out.println("#\tStudents: " + students);
        System.out.println("#\tCourses: " + courses);
        System.out.println("#\tCourses per Student:" + coursesPerStudent);
        System.out.println("#\tInitialize: " + initialize);
        System.out.println("#\tMetadata: " + metadata);
        for (int i = 0; i < wait; ++i) {
            System.out.print((i + 1) + " ... ");
            Thread.sleep(1000);
        }
        System.out.println("Started...");

        List<Experiment> experiments = new ArrayList<Experiment>();

        for (Character solution : solutions) {
            EntityManager em = null;
            switch (solution) {
                case '0':
                    em = new EntityManagerS0();
                    break;
                case '1':
                    em = new EntityManagerS1();
                    break;
                case '2':
                    em = new EntityManagerS2();
                    break;
                case '3':
                    em = new EntityManagerS3();
                    break;
                case '4':
                    em = new EntityManagerS4();
                    break;
                default:
                    throw new AssertionError();
            }
            String code = em.getValidationHandler().solution();
            Experiment experiment = new Experiment(code, em, metadata);
            Recorder recorder = new Recorder("solution" + solution, "", logPath);
            recorder.initialize();
            experiment.setRecorder(recorder);
            experiments.add(experiment);
        }

        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(students);
        ad.setNumberOfCourses(courses);
        ad.setNumberOfCoursesPerStudent(coursesPerStudent);
        ad.setMetadata(metadata);

        for (Experiment experiment : experiments) {
            System.out.println("#EXPERIMENT: " + experiment.getCode());
            String out = "#\tWorking Directory" + System.getProperty("user.dir") + "\n";
            out += "#\tSolution: " + experiment.getCode() + "\n";
            out += "#\tLogPath: " + logPath + "\n";
            out += "#\tRuns: " + runs + "\n";
            out += "#\tStudents: " + students + "\n";
            out += "#\tCourses: " + courses + "\n";
            out += "#\tCourses Per Student: " + coursesPerStudent + "\n";
            out += "#\tInitialize: " + initialize + "\n";
            out += "#\tMetadata: " + metadata + "\n";

            System.out.println(out);
            experiment.getRecorder().log(out);


            if (initialize) {
                log.info("Initializing experiment " + experiment.getCode());
                experiment.initialize();
                log.info("Finished experiment " + experiment.getCode());
            } else {
                log.info("Experimenting " + experiment.getCode());
                experiment.setStudents(ad.generateStudents());
                experiment.setCourses(ad.generateCourses());
                experiment.setEnrolments(ad.generateEnrolment());
                experiment.run(runs);
                //get rid of entities for memory efficiency
                experiment.setStudents(null);
                experiment.setCourses(null);
                experiment.setEnrolments(null);
                System.gc();
                log.info("Finished experiment " + experiment.getCode());
            }
            experiment.getRecorder().close();
        }
        System.out.println("#Total duration: " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("#Number of Connections:" + CloudConnector.NUMBER_OF_CONNECTIONS);

        CloudConnector.shutdown();
    }
}
