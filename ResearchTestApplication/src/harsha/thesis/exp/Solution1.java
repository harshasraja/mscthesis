/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.solution1.dao.BaseDAO;
import harsha.thesis.api.solution1.entity.BaseEntity;
import harsha.thesis.api.solution1.entity.Course;
import harsha.thesis.api.solution1.entity.Enrolment;
import harsha.thesis.api.solution1.entity.User;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;


/**
 *
 * @author jcrada
 */
public class Solution1 implements SolutionExperiment {

    private static DecimalFormat DF = new DecimalFormat("# ### ###, ###");
    private static Logger log = Logger.getLogger(Solution1.class);
    
    private Experiment experiment;
    private String[] csvFiles;
    //
    private List<User> users;
    private List<Course> courses;
    private List<Enrolment> enrolments;
    private BaseDAO dao;

    public Solution1(Experiment experiment, String[] csvFiles) {
        this.experiment = experiment;
        this.csvFiles = csvFiles;
    }

    public void initialize() throws Exception {
        dao = new BaseDAO();

        harsha.thesis.exp.entity.User defaultUser = CommonHelper.GetDefaultUser();
        harsha.thesis.exp.entity.Course defaultCourse = CommonHelper.getDefaultCourse();
        harsha.thesis.exp.entity.Enrolment defaultEnrolment = CommonHelper.getDefaultEnrolment();

        User user = new User();
        user.setAge(defaultUser.getAge());
        user.setEmail(defaultUser.getEmail());
        user.setFirstName(defaultUser.getFirstName());
        user.setLastName(defaultUser.getLastName());
        user.setType(defaultUser.getType());
        user.setUserId(defaultUser.getUserId());
        dao.insert(user);

        Course course = new Course();
        course.setCourseId(defaultCourse.getCourseId());
        course.setCourseName(defaultCourse.getCourseName());
        course.setLevel(defaultCourse.getLevel());
        course.setTrimister(defaultCourse.getTrimister());
        course.setYear(defaultCourse.getYear());
        dao.insert(course);

        Enrolment enrolment = new Enrolment();
        enrolment.setCourseId(defaultEnrolment.getCourseId());
        enrolment.setRowId(defaultEnrolment.getRowId());
        enrolment.setType(defaultEnrolment.getType());
        enrolment.setUserId(defaultEnrolment.getUserId());
        dao.insert(enrolment);

        dao.close();
    }

    public void experiment(int runs) throws Exception {
        users = CommonHelper.GetUserEntities(Solution.ONE, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.ONE, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.ONE, csvFiles[2]);

        dao = new BaseDAO();
        

        for (int i = 0; i < runs; ++i) {
            log.info("Run " + i);
            experiment.log("#RUN:" + (i + 1));
            String newCourseId = (i + 1) % 2 == 0 ? ArtificialData.COURSE_BASE_NAME
                    : ArtificialData.COURSE_ALTERNATIVE_NAME;
            long start = System.nanoTime();
            log.info("Inserting");
            insert();
            log.info("Total Insterted [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
//            updateCourse(newCourseId);

            start = System.nanoTime();
            log.info("Updating Enrolment");
            updateEnrolment();
            log.info("Total Updating Enrolment [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

            start = System.nanoTime();
            log.info("Delete");
            delete();
            log.info("Total Deleted [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

            increaseIds();
        }
        dao.close();
    }

    public void increaseIds() {
        for (User user : users) {
            long currentUserId = Long.parseLong(user.getUserId()) + users.size();
            user.setUserId("" + currentUserId);
            user.setFirstName("First Name (" + currentUserId + ")");
            user.setLastName("Last Name (" + currentUserId + ")");
            user.setEmail("First.Last@email." + currentUserId + ".com");
        }


        for (Course course : courses) {
            long currentCourseId = Long.parseLong(course.getCourseId().substring(ArtificialData.COURSE_BASE_NAME.length()))
                    + courses.size();

            course.setCourseId(course.getCourseId().substring(0, ArtificialData.COURSE_BASE_NAME.length()) + currentCourseId);
            course.setCourseName("Engineering (" + currentCourseId + ")");
        }


        for (Enrolment enrolment : enrolments) {
            long currentEnrolmentId = Long.parseLong(enrolment.getRowId()) + enrolments.size();

            enrolment.setRowId("" + currentEnrolmentId);

            long currentUserId = Long.parseLong(enrolment.getUserId()) + users.size();
            enrolment.setUserId("" + currentUserId);

            long currentCourseId = Long.parseLong(enrolment.getCourseId().substring(ArtificialData.COURSE_BASE_NAME.length()))
                    + courses.size();

            enrolment.setCourseId(enrolment.getCourseId().substring(0, ArtificialData.COURSE_BASE_NAME.length()) + currentCourseId);
        }

    }

    private void insert() throws Exception {
        Random random = new Random(Main.INSERT_RANDOM_SEED);
        List<BaseEntity> usersToInsert = new ArrayList<BaseEntity>(users);
        Collections.shuffle(usersToInsert, random);

        List<BaseEntity> coursesToInsert = new ArrayList<BaseEntity>(courses);
        Collections.shuffle(coursesToInsert, random);

        List<BaseEntity> enrolmentsToInsert = new ArrayList<BaseEntity>(enrolments);
        Collections.shuffle(enrolmentsToInsert, random);

        //Users
        experiment.log("#INSERT\n");
        experiment.start();
        for (BaseEntity entity : usersToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_user:" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_course:" + experiment.duration() + "\n");

        //Enrolments

        experiment.start();
        for (BaseEntity entity : enrolmentsToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_enrolment:" + experiment.duration() + "\n\n");
    }

    private void updateCourse(String newBaseKey) throws Exception {
        Random random = new Random(Main.UPDATE_COURSE_RANDOM_SEED);
        List<Course> coursesToUpdate = new ArrayList<Course>(courses);
        Collections.shuffle(coursesToUpdate, random);

        experiment.start();
        for (Course entity : coursesToUpdate) {
            String keyForUpdate = newBaseKey + entity.getCourseId().substring(newBaseKey.length());
            entity.setKeyForUpdate(keyForUpdate);
            dao.update(entity);

        }
        experiment.stop();
        experiment.log("update_course:" + experiment.duration() + "\n\n");
    }

    private void updateEnrolment() throws Exception {
        Random random = new Random(Main.UPDATE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToUpdate = new ArrayList<Enrolment>(enrolments);
        Collections.shuffle(enrolmentsToUpdate, random);

        experiment.start();
        Iterator<Enrolment> it = enrolmentsToUpdate.iterator();
        for (Enrolment entity : enrolments) {
            entity.setCourseId(it.next().getCourseId());
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("update_enrolment:" + experiment.duration() + "\n\n");
    }

    private void delete() throws Exception {
        Random random = new Random(Main.DELETE_RANDOM_SEED);
        List<BaseEntity> usersToDelete = new ArrayList<BaseEntity>(users);
        Collections.shuffle(usersToDelete, random);

        List<BaseEntity> coursesToDelete = new ArrayList<BaseEntity>(courses);
        Collections.shuffle(coursesToDelete, random);

        List<BaseEntity> enrolmentsToDelete = new ArrayList<BaseEntity>(enrolments);
        Collections.shuffle(enrolmentsToDelete, random);

//Enrolment
        experiment.log("#DELETE\n");
        experiment.start();
        for (BaseEntity entity : enrolmentsToDelete) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_enrolment:" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToDelete) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_course:" + experiment.duration() + "\n");

        //USers
        experiment.start();
        for (BaseEntity entity : usersToDelete) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_user:" + experiment.duration() + "\n\n");

    }
}
