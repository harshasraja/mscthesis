/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import harsha.api.Constraint;
import harsha.api.EntityManager;
import harsha.api.example.Course;
import harsha.api.example.Enrolment;
import harsha.api.example.Student;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author harshasraja
 */
public class Experiment {

    protected static final Logger log = Logger.getLogger(Experiment.class);
    protected static final DecimalFormat DF = new DecimalFormat("# ### ###, ###");
    public static final String INSERT_STUDENT = "insert_student";
    public static final String INSERT_COURSE = "insert_course";
    public static final String INSERT_ENROLMENT = "insert_enrolment";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String UPDATE_COURSE = "update_course";
    public static final String UPDATE_ENROLMENT = "update_enrolment";
    public static final String DELETE_STUDENT = "delete_student";
    public static final String DELETE_COURSE = "delete_course";
    public static final String DELETE_ENROLMENT = "delete_enrolment";
    private static final long INSERT_STUDENT_RANDOM_SEED = INSERT_STUDENT.hashCode();
    private static final long INSERT_COURSE_RANDOM_SEED = INSERT_COURSE.hashCode();
    private static final long INSERT_ENROLMENT_RANDOM_SEED = INSERT_ENROLMENT.hashCode();
    private static final long UPDATE_STUDENT_RANDOM_SEED = UPDATE_STUDENT.hashCode();
    private static final long UPDATE_COURSE_RANDOM_SEED = UPDATE_COURSE.hashCode();
    private static final long UPDATE_ENROLMENT_RANDOM_SEED = UPDATE_ENROLMENT.hashCode();
    private static final long DELETE_STUDENT_RANDOM_SEED = DELETE_STUDENT.hashCode();
    private static final long DELETE_COURSE_RANDOM_SEED = DELETE_COURSE.hashCode();
    private static final long DELETE_ENROLMENT_RANDOM_SEED = DELETE_ENROLMENT.hashCode();
    private String code;
    private EntityManager em;
    private Recorder recorder;
    private List<Student> students;
    private List<Course> courses;
    private List<Enrolment> enrolments;
    private String metadata;
    private String courseBaseName = ArtificialData.COURSE_BASENAME;
    private static final String AlternativeCourseBaseName = "SWEN";

    public Experiment(String code, EntityManager em, String metadata) {
        this.code = code;
        this.em = em;
        this.metadata = metadata;
    }

    public void initialize() throws Exception {
        em.createColumFamily(Student.class, true);
        em.createColumFamily(Course.class, true);
        em.createColumFamily(Enrolment.class, true);

        if ("solution2".equals(em.getValidationHandler().solution())) {
            Student studentMetadata = new Student();
            studentMetadata.setStudentId("-1");
            studentMetadata.setMetadata(getMetadata());
            em.insert(studentMetadata);

            Course courseMetadata = new Course();
            courseMetadata.setCourseId("-1");
            courseMetadata.setMetadata(getMetadata());
            em.insert(courseMetadata);

            Enrolment enrolmentMetadata = new Enrolment();
            enrolmentMetadata.setRowId("-1");
            enrolmentMetadata.setMetadata(getMetadata());
            em.insert(enrolmentMetadata);
        } else if ("solution3".equals(em.getValidationHandler().solution())
                || "solution4".equals(em.getValidationHandler().solution())) {
            em.createColumFamily(Constraint.class, true);
            List<Constraint> constraints = Constraint.Parse(getMetadata());
            for (Constraint constraint : constraints) {
                em.insert(constraint);
            }
        }

    }

    public void run(int times) throws Exception {
        for (int i = 0; i < times; ++i) {
            log.info("Run " + i);
            recorder.log("#RUN:" + (i + 1));

            long start = System.nanoTime();
            log.info("INSERT");
            insert();
            log.info("Total INSERT [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

            start = System.nanoTime();
            log.info("UPDATE");
            update();
            log.info("Total UPDATE [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

            start = System.nanoTime();
            log.info("DELETE");
            delete();
            log.info("Total DELETE [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

            increaseIds();
        }
    }

    private void insert() throws Exception {
        long start = System.nanoTime();
        insertStudent();
        log.info("insertStudent() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        insertCourse();
        log.info("insertCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        insertEnrolment();
        log.info("insertEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void update() throws Exception {
        long start = System.nanoTime();
        updateCourse(); //Exception is thrown yet catched therein, no changes to courses
        if (ArtificialData.COURSE_BASENAME.equals(courseBaseName)){
            courseBaseName = AlternativeCourseBaseName;
        }else{
            courseBaseName = ArtificialData.COURSE_BASENAME;
        }
        log.info("updateCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        updateEnrolment(); //Foreign keys updated
        log.info("updateEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        updateStudent(); //Primary keys updated and cascaded
        log.info("updateUser() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void delete() throws Exception {
        long start = System.nanoTime();
        deleteEnrolment(); //Delete all fields with no check
        log.info("deleteEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        deleteStudent(); //delete all students and cascade to enrolment
        log.info("deleteUser() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        deleteCourse(); //delete all courses as enrolment is empty
        log.info("deleteCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void insertStudent() throws Exception {
        Random random = new Random(INSERT_STUDENT_RANDOM_SEED);
        List<Student> studentsToInsert = new LinkedList<Student>(students);
        Collections.shuffle(studentsToInsert, random);

        //Users
        recorder.log("#INSERT\n");
        recorder.start();
        for (Student entity : studentsToInsert) {
            em.insert(entity);
        }
        recorder.stop();
        recorder.log(INSERT_STUDENT + ":" + recorder.duration() + "\n");

    }

    private void insertCourse() throws Exception {
        Random random = new Random(INSERT_COURSE_RANDOM_SEED);
        List<Course> coursesToInsert = new LinkedList<Course>(courses);
        Collections.shuffle(coursesToInsert, random);

        recorder.start();
        for (Course entity : coursesToInsert) {
            em.insert(entity);
        }
        recorder.stop();
        recorder.log(INSERT_COURSE + ":" + recorder.duration() + "\n");
    }

    private void insertEnrolment() throws Exception {
        Random random = new Random(INSERT_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToInsert = new LinkedList<Enrolment>(enrolments);
        Collections.shuffle(enrolmentsToInsert, random);

        recorder.start();
        for (Enrolment entity : enrolmentsToInsert) {
            em.insert(entity);
        }
        recorder.stop();
        recorder.log(INSERT_ENROLMENT + ":" + recorder.duration() + "\n");
    }

    private void updateStudent() throws Exception {
        Random random = new Random(UPDATE_STUDENT_RANDOM_SEED);
        List<Student> studentsToUpdate = new LinkedList<Student>(students);
        Collections.shuffle(studentsToUpdate, random);

        recorder.start();
        for (Student entity : studentsToUpdate) {
            Integer studentId = Integer.parseInt(entity.getStudentId());
            entity.setKeyForUpdate("" + (studentId * -1));
            entity.setAge("" + (Integer.parseInt(entity.getAge()) * -1));
            em.update(entity);
        }
        recorder.stop();
        recorder.log(UPDATE_STUDENT + ":" + recorder.duration() + "\n");
        
        
        for (Enrolment entity : enrolments) {
            entity.setStudentId("" + (Long.parseLong(entity.getStudentId()) * -1));
        }
    }

    private void updateCourse() throws Exception {
        Random random = new Random(UPDATE_COURSE_RANDOM_SEED);
        List<Course> coursesToUpdate = new LinkedList<Course>(courses);
        Collections.shuffle(coursesToUpdate, random);

        
        recorder.start();
        for (Course entity : coursesToUpdate) {
            entity.setKeyForUpdate(courseBaseName +
                    entity.getCourseId().substring(courseBaseName.length()));
            try {
                em.update(entity);
            } catch (Exception ex) {
//                log.info(ex);
            }
            entity.setKeyForUpdate("");
        }
        recorder.stop();
        recorder.log(UPDATE_COURSE + ":" + recorder.duration() + "\n");
        
        if ("solution0".equals(em.getValidationHandler().solution())){
            for (Enrolment entity :enrolments){
                entity.setCourseId(courseBaseName +
                    entity.getCourseId().substring(courseBaseName.length()));
            }
        }
        
    }

    private void updateEnrolment() throws Exception {
        Random random = new Random(UPDATE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToUpdate = new LinkedList<Enrolment>();
        for (Enrolment entity : enrolments) {
            Enrolment clone = (Enrolment) entity.clone();
            enrolmentsToUpdate.add(clone);
        }

        Collections.shuffle(enrolmentsToUpdate, random);

        recorder.start();
        Iterator<Enrolment> it = enrolmentsToUpdate.iterator();
        for (Enrolment entity : enrolments) {
            entity.setCourseId(it.next().getCourseId());
            em.update(entity);
        }
        recorder.stop();
        recorder.log(UPDATE_ENROLMENT + ":" + recorder.duration() + "\n");
    }

    private void deleteStudent() throws Exception {
        Random random = new Random(DELETE_STUDENT_RANDOM_SEED);
        List<Student> studentsToDelete = new LinkedList<Student>(students);
        Collections.shuffle(studentsToDelete, random);

        recorder.start();
        for (Student entity : studentsToDelete) {
            em.delete(entity);
        }
        recorder.stop();
        recorder.log(DELETE_STUDENT + ":" + recorder.duration() + "\n");
    }

    private void deleteCourse() throws Exception {
        Random random = new Random(DELETE_COURSE_RANDOM_SEED);
        List<Course> coursesToDelete = new LinkedList<Course>(courses);
        Collections.shuffle(coursesToDelete, random);

        recorder.start();
        for (Course entity : coursesToDelete) {
            em.delete(entity); //NO DELETE
        }
        recorder.stop();
        recorder.log(DELETE_COURSE + ":" + recorder.duration() + "\n");
    }

    private void deleteEnrolment() throws Exception {
        Random random = new Random(DELETE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToDelete = new LinkedList<Enrolment>(enrolments);
        Collections.shuffle(enrolmentsToDelete, random);
        recorder.start();
        for (Enrolment entity : enrolmentsToDelete) {
            em.delete(entity);
        }
        recorder.stop();
        recorder.log(DELETE_ENROLMENT + ":" + recorder.duration() + "\n");

        if (!"solution0".equals(em.getValidationHandler().solution())){
            //reinsert
            for (Enrolment entity : enrolments) {
                em.insert(entity);
            }
        }
    }

    public void increaseIds() {
        for (Student user : students) {
            long studentId = Long.parseLong(user.getStudentId());
            studentId += (studentId < 0 ? -students.size() : students.size());
            user.setStudentId("" + studentId);
            user.setFirstName("First Name (" + studentId + ")");
            user.setLastName("Last Name (" + studentId + ")");
            user.setEmail("First.Last@email." + studentId + ".com");
        }


        for (Course course : courses) {
            long courseId = Long.parseLong(
                    course.getCourseId().substring(ArtificialData.COURSE_BASENAME.length()))
                    + courses.size();

            course.setCourseId(ArtificialData.COURSE_BASENAME + courseId);
            course.setCourseName("Engineering (" + courseId + ")");
        }


        for (Enrolment enrolment : enrolments) {
            long enrolmentId = Long.parseLong(enrolment.getRowId()) + enrolments.size();

            enrolment.setRowId("" + enrolmentId);

            long studentId = Long.parseLong(enrolment.getStudentId());
            studentId += (studentId < 0 ? -students.size() : students.size());
            enrolment.setStudentId("" + studentId);

            long currentCourseId = Long.parseLong(enrolment.getCourseId().substring(ArtificialData.COURSE_BASENAME.length()))
                    + courses.size();

            enrolment.setCourseId(ArtificialData.COURSE_BASENAME + currentCourseId);
        }

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRecorder(Recorder recorder) {
        this.recorder = recorder;
    }

    public Recorder getRecorder() {
        return this.recorder;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
