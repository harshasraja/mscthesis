/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import harsha.api.connection.CloudConnector;
import harsha.api.example.Course;
import harsha.api.example.Enrolment;
import harsha.api.example.Student;
import harsha.api.solutions.em.EntityManagerS2;
import java.io.File;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author subramhars
 */
public class ReferentialIntegrityTest {

    protected static final Logger LOG = Logger.getLogger(ReferentialIntegrityTest.class);
    private static int FINE = 0;
    private EntityManager em;
    private ArtificialData ad;

    public ReferentialIntegrityTest(EntityManager em, ArtificialData ad) {
        this.em = em;
        this.ad = ad;
    }

    public void createColumnFamilies() throws Exception {
        em.createColumFamily(Student.class, true);
        em.createColumFamily(Course.class, true);
        em.createColumFamily(Enrolment.class, true);
    }

    public void createMetadataSolution2() throws Exception {
        Student studentMetadata = new Student();
        studentMetadata.setStudentId("-1");
        studentMetadata.setMetadata(ad.getMetadata());
        em.insert(studentMetadata);

        Course courseMetadata = new Course();
        courseMetadata.setCourseId("-1");
        courseMetadata.setMetadata(ad.getMetadata());
        em.insert(courseMetadata);

        Enrolment enrolmentMetadata = new Enrolment();
        enrolmentMetadata.setRowId("-1");
        enrolmentMetadata.setMetadata(ad.getMetadata());
        em.insert(enrolmentMetadata);
    }

    public void createMetadataAsEntity() throws Exception {
        em.createColumFamily(Constraint.class, true);

        List<Constraint> constraints = Constraint.Parse(ad.getMetadata());
        for (Constraint constraint : constraints){
            em.insert(constraint);
        }
    }

    public void insert() throws Exception {
        for (Entity entity : ad.generateStudents()) {
            em.insert(entity);
        }

        for (Entity entity : ad.generateCourses()) {
            em.insert(entity);
        }

        for (Entity entity : ad.generateEnrolment()) {
            em.insert(entity);
        }

        Enrolment wrong = new Enrolment("RIGHT", "WRONG", "COMP100", "WRONG");
        wrong.setMetadata(ad.getMetadata());
        try {
            em.insert(wrong);
        } catch (Exception ex) {

            LOG.debug("FINE:" + ++FINE + ex);
        }

        wrong.setStudentId("100");
        wrong.setCourseId("WRONG");
        try {
            em.insert(wrong);

        } catch (Exception ex) {
            LOG.debug("FINE: " + ++FINE + ex);
        }
    }

    public void update() throws Exception {
        List<Enrolment> enrolments = ad.generateEnrolment();
        Enrolment enrolment = enrolments.get(0);
        enrolment.setStudentId("WRONG");

        try {
            em.update(enrolment);
        } catch (Exception ex) {
            LOG.debug("FINE: " + ++FINE + ex);
        }

        enrolment = enrolments.get(1);
        enrolment.setCourseId("WRONG");

        try {
            em.update(enrolment);
        } catch (Exception ex) {
            LOG.debug("FINE: " + ++FINE + ex);
        }

        List<Course> courses = ad.generateCourses();
        Course course = courses.get(0);

        course.setKeyForUpdate("WRONG");
        try {
            em.update(course);
        } catch (Exception ex) {
            LOG.debug("FINE: " + ++FINE + ex);
        }

        List<Student> students = ad.generateStudents();
        Student student = students.get(0);

        student.setKeyForUpdate("RIGHT");
        try {
            em.update(student);
            LOG.debug("FINE: " + ++FINE);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void delete() throws Exception {

        int exceptions = 0;
        for (Course course : ad.generateCourses()) {
            try {
                em.delete(course);
            } catch (Exception ex) {
                ++exceptions;
            }
        }

        if (exceptions != ad.generateCourses().size()) {
            throw new Exception("WRONG");
//            LOG.fatal("WRONG");
        }

        for (Enrolment enrolment : ad.generateEnrolment()) {
            em.delete(enrolment);
        }


        for (Course course : ad.generateCourses()) {
            em.delete(course);
        }

        for (Student student : ad.generateStudents()) {
            em.delete(student);
        }


        insert();

        for (Student student : ad.generateStudents()) {
            em.delete(student);
        }

        for (Course course : ad.generateCourses()) {
            em.delete(course);
        }

    }

    public static void test(EntityManager em) throws Exception {
        File csv = new File("/home/phoenix1/subramhars/workspace/ResearchTestApplication/data/Solution3/Metadata.csv");
        List<Constraint> metadata = Constraint.ParseFromCsv(csv);
        String stringOfMetadata = Constraint.ToString(metadata);

        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(5);
        ad.setNumberOfCourses(5);
        ad.setNumberOfCoursesPerStudent(5);
        ad.setMetadata(stringOfMetadata);
        ReferentialIntegrityTest test = new ReferentialIntegrityTest(em, ad);
        test.createColumnFamilies();
        test.createMetadataAsEntity();
        test.createMetadataSolution2();
//        test.insert();
//        test.update();
//        test.delete();
    }

    public static void main(String[] args) throws Exception {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        EntityManager em = new EntityManagerS2();
        test(em);
        CloudConnector.shutdown();
    }
}
