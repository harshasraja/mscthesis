/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import harsha.api.Entity;
import harsha.api.example.Course;
import harsha.api.example.Enrolment;
import harsha.api.example.Student;
import harsha.thesis.exp.MyMath;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.cassandra.utils.Pair;
import org.apache.log4j.Logger;

/**
 *
 * @author jcrada
 */
public class ArtificialData {

    protected static final Logger LOG = Logger.getLogger(ArtificialData.class);
    public static final int INITIAL_ID = 100;
    public static final String COURSE_BASENAME = "COMP";
    private int numberOfStudents;
    private int numberOfCourses;
    private int numberOfCoursesPerStudent;

    public ArtificialData() {
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public int getNumberOfCoursesPerStudent() {
        return numberOfCoursesPerStudent;
    }

    public void setNumberOfCoursesPerStudent(int numberOfCoursesPerStudent) {
        this.numberOfCoursesPerStudent = numberOfCoursesPerStudent;
    }

    public List<Student> generateStudents() {
        List<Student> result = new ArrayList<Student>();

        Random age = new Random(1l);
        int id = INITIAL_ID;
        for (int i = 0; i < numberOfStudents; ++i) {
            Student entity = new Student();
            Entity.SetValue("StudentId", "" + id, entity);
            Entity.SetValue("FirstName", "First Name (" + id + ")", entity);
            Entity.SetValue("LastName", "Last Name (" + id + ")", entity);
            Entity.SetValue("Email", "First.Last@email." + id + ".com", entity);
            Entity.SetValue("Age", "" + (18 + age.nextInt(50)), entity);
            Entity.SetValue("Type", "STUDENT", entity);
            result.add(entity);
            ++id;
        }

        return result;
    }

    public List<Course> generateCourses() {
        List<Course> result = new ArrayList<Course>();
        Random random = new Random(1l);
        int id = INITIAL_ID;
        for (int i = 0; i < numberOfCourses; ++i) {
            Course entity = new Course();
            Entity.SetValue("CourseId", COURSE_BASENAME + id, entity);
            Entity.SetValue("CourseName", "Engineering (" + id + ")", entity);
            Entity.SetValue("Trimester", "" + (1 + random.nextInt(3)), entity);
            Entity.SetValue("Level", "" + (1 + random.nextInt(4)), entity);
            Entity.SetValue("Year", "" + (random.nextBoolean() ? "2012" : "2011"), entity);
            result.add(entity);
            ++id;
        }
        return result;
    }

    public List<Enrolment> generateEnrolment() {
        List<Enrolment> result = new ArrayList<Enrolment>();

        int increment = getNumberOfCourses() / getNumberOfCoursesPerStudent();
        int studentId = INITIAL_ID;
        int rowId = INITIAL_ID;
        int initialCourseId = 0;
        for (int i = 0; i < getNumberOfStudents(); ++i) {
            int courseId = initialCourseId % getNumberOfCourses();
            for (int j = 0; j < getNumberOfCoursesPerStudent(); ++j) {
                Enrolment entity = new Enrolment();
                Entity.SetValue("RowId", "" + rowId, entity);
                Entity.SetValue("StudentId", "" + studentId, entity);
                Entity.SetValue("CourseId", COURSE_BASENAME
                        + (INITIAL_ID + (courseId % getNumberOfCourses())), entity);
                Entity.SetValue("Type", "STUDENT", entity);
                LOG.warn("TODO: Check STUDENT??");
                result.add(entity);
                ++rowId;
                courseId += increment;
            }
            ++studentId;
            ++initialCourseId;
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(100);
        ad.setNumberOfCourses(100);
        ad.setNumberOfCoursesPerStudent(10);

        File file;
        BufferedWriter writer;

        file = new File("/tmp/student.txt");
        writer = new BufferedWriter(new FileWriter(file));
        for (Student student : ad.generateStudents()) {
            writer.write(student.toString() + "\n");
            writer.flush();
        }
        writer.close();

        file = new File("/tmp/course.txt");
        writer = new BufferedWriter(new FileWriter(file));
        for (Course course : ad.generateCourses()) {
            writer.write(course.toString() + "\n");
            writer.flush();
        }
        writer.close();
        file = new File("/tmp/enrolment.txt");
        writer = new BufferedWriter(new FileWriter(file));
        for (Enrolment enrolment : ad.generateEnrolment()) {
            writer.write(enrolment.toString() + "\n");
            writer.flush();
        }
        writer.close();


    }
}
