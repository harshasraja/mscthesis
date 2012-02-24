/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.solution0.dao.BaseDAO;
import harsha.thesis.api.solution0.entity.BaseEntity;
import harsha.thesis.api.solution0.entity.Course;
import harsha.thesis.api.solution0.entity.Enrolment;
import harsha.thesis.api.solution0.entity.User;
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
public class Solution0 implements SolutionExperiment {

    private static DecimalFormat DF = new DecimalFormat("# ### ###, ###");
    private static Logger log = Logger.getLogger(Solution0.class);
    private Experiment experiment;
    private String[] csvFiles;
    //
    private List<User> users;
    private List<Course> courses;
    private List<Enrolment> enrolments;
    private BaseDAO dao;

    public Solution0(Experiment experiment, String[] csvFiles) {
        this.experiment = experiment;
        this.csvFiles = csvFiles;
    }

    @Override
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
        users = CommonHelper.GetUserEntities(Solution.ZERO, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.ZERO, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.ZERO, csvFiles[2]);

        dao = new BaseDAO();


        for (int i = 0; i < runs; ++i) {
            log.info("Run " + i);
            experiment.log("#RUN:" + (i + 1));

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
        dao.close();
    }

    public void increaseIds() {
        for (User user : users) {
            long currentUserId = Long.parseLong(user.getUserId());
            currentUserId += (currentUserId < 0 ? -users.size() : users.size());
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

            long currentUserId = Long.parseLong(enrolment.getUserId());
            currentUserId += (currentUserId < 0 ? -users.size() : users.size());
            enrolment.setUserId("" + currentUserId);

            long currentCourseId = Long.parseLong(enrolment.getCourseId().substring(ArtificialData.COURSE_BASE_NAME.length()))
                    + courses.size();

            enrolment.setCourseId(enrolment.getCourseId().substring(0, ArtificialData.COURSE_BASE_NAME.length()) + currentCourseId);
        }

    }

    private void insert() throws Exception {
        long start = System.nanoTime();
        insertUser();
        log.info("insertUser() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        insertCourse();
        log.info("insertCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        insertEnrolment();
        log.info("insertEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void update() throws Exception {
        long start = System.nanoTime();
        updateCourse();
        log.info("updateCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        updateEnrolment();
        log.info("updateEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        updateUser();
        log.info("updateUser() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void delete() throws Exception {
        long start = System.nanoTime();
        deleteEnrolment();
        log.info("deleteEnrolment() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");

        start = System.nanoTime();
        deleteUser();
        log.info("deleteUser() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
        
        start = System.nanoTime();
        deleteCourse();
        log.info("deleteCourse() [" + DF.format((System.nanoTime() - start) / 1000.0) + "]");
    }

    private void insertUser() throws Exception {
        Random random = new Random(Main.INSERT_USER_RANDOM_SEED);
        List<BaseEntity> usersToInsert = new ArrayList<BaseEntity>(users);
        Collections.shuffle(usersToInsert, random);

        //Users
        experiment.log("#INSERT\n");
        experiment.start();
        for (BaseEntity entity : usersToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_user:" + experiment.duration() + "\n");

    }

    private void insertCourse() throws Exception {
        Random random = new Random(Main.INSERT_COURSE_RANDOM_SEED);
        List<BaseEntity> coursesToInsert = new ArrayList<BaseEntity>(courses);
        Collections.shuffle(coursesToInsert, random);

        experiment.start();
        for (BaseEntity entity : coursesToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_course:" + experiment.duration() + "\n");
    }

    private void insertEnrolment() throws Exception {
        Random random = new Random(Main.INSERT_ENROLMENT_RANDOM_SEED);
        List<BaseEntity> enrolmentsToInsert = new ArrayList<BaseEntity>(enrolments);
        Collections.shuffle(enrolmentsToInsert, random);

        experiment.start();
        for (BaseEntity entity : enrolmentsToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_enrolment:" + experiment.duration() + "\n\n");
    }

    private void updateUser() throws Exception {
        Random random = new Random(Main.UPDATE_USER_RANDOM_SEED);
        List<User> usersToUpdate = new ArrayList<User>(users);
        Collections.shuffle(usersToUpdate, random);

        experiment.start();
        for (User entity : usersToUpdate) {
            Integer userId = Integer.parseInt(entity.getUserId());
            entity.setKeyForUpdate("" + (userId * -1));
            entity.setAge("" + (Integer.parseInt(entity.getAge()) * -1));
            log.info("BEFORE UPDATE: " + entity);
            dao.update(entity);
            log.info("AFTER UPDATE: " + entity);
        }
        experiment.stop();
        experiment.log("update_user:" + experiment.duration() + "\n\n");
    }

    private void updateCourse() throws Exception {
        Random random = new Random(Main.UPDATE_COURSE_RANDOM_SEED);
        List<Course> coursesToUpdate = new ArrayList<Course>(courses);
        Collections.shuffle(coursesToUpdate, random);

        experiment.start();
        for (Course entity : coursesToUpdate) {
            //dummy set as exception to be thrown
            entity.setKeyForUpdate(entity.getCourseId());
            try {
                dao.update(entity);
            } catch (Exception ex) {
                log.info(ex);
            }
        }
        experiment.stop();
        experiment.log("update_course:" + experiment.duration() + "\n\n");
    }

    private void updateEnrolment() throws Exception {
        Random random = new Random(Main.UPDATE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToUpdate = new ArrayList<Enrolment>();
        for (Enrolment entity : enrolments) {
            Enrolment clone = (Enrolment) entity.clone();
            enrolmentsToUpdate.add(clone);
        }

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

    private void deleteUser() throws Exception {
        Random random = new Random(Main.DELETE_USER_RANDOM_SEED);
        List<BaseEntity> usersToDelete = new ArrayList<BaseEntity>(users);
        Collections.shuffle(usersToDelete, random);

        experiment.start();
        for (BaseEntity entity : usersToDelete) {
            dao.delete(entity); 
        }
        experiment.stop();
        experiment.log("delete_user:" + experiment.duration() + "\n\n");
    }

    private void deleteCourse() throws Exception {
        Random random = new Random(Main.DELETE_COURSE_RANDOM_SEED);
        List<BaseEntity> coursesToDelete = new ArrayList<BaseEntity>(courses);
        Collections.shuffle(coursesToDelete, random);

        experiment.start();
        for (BaseEntity entity : coursesToDelete) {
            try {
                dao.delete(entity); //NO DELETE
            } catch (Exception ex) {
//                log.info(ex); //Ignore Exception
            }
        }
        experiment.stop();
        experiment.log("delete_course:" + experiment.duration() + "\n\n");
    }

    private void deleteEnrolment() throws Exception {
        experiment.start();
        for (BaseEntity entity : enrolments) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_enrolment:" + experiment.duration() + "\n");
    }
}
