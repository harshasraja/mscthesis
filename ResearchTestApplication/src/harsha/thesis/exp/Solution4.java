/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.connection.ConnectionDefinition;
import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.api.solution4.dao.BaseDAO;
import harsha.thesis.api.solution4.dao.ValidationHandler;
import harsha.thesis.api.solution4.entity.BaseEntity;
import harsha.thesis.api.solution4.entity.Course;
import harsha.thesis.api.solution4.entity.Enrolment;
import harsha.thesis.api.solution4.entity.Metadata;
import harsha.thesis.api.solution4.entity.User;
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
public class Solution4 implements SolutionExperiment {

    private static DecimalFormat DF = new DecimalFormat("# ### ###, ###");
    private static Logger log = Logger.getLogger(Solution4.class);
    private Experiment experiment;
    private String[] csvFiles;
    //
    private List<User> users;
    private List<Course> courses;
    private List<Enrolment> enrolments;
    private BaseDAO daoMetadata, daoUser, daoCourse, daoEnrolment;

    public Solution4(Experiment experiment, String[] csvFiles) {
        this.experiment = experiment;
        this.csvFiles = csvFiles;
    }

    public void initialize() throws Exception {
        users = CommonHelper.GetUserEntities(Solution.FOUR, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.FOUR, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.FOUR, csvFiles[2]);


        daoMetadata = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.entity.Metadata"));
        daoMetadata.insert(new Metadata("CONST100", "UNIVERSITY", "P", "harsha_thesis_api_solution4_entity_User", "UNIVERSITY", "", "UserId", ""));
        daoMetadata.insert(new Metadata("CONST200", "UNIVERSITY", "P", "harsha_thesis_api_solution4_entity_Course", "UNIVERSITY", "", "CourseId", ""));
        daoMetadata.insert(new Metadata("CONST300", "UNIVERSITY", "P", "harsha_thesis_api_solution4_entity_Enrolment", "UNIVERSITY", "", "RowID", ""));
        daoMetadata.insert(new Metadata("CONST400", "UNIVERSITY", "R", "harsha_thesis_api_solution4_entity_Enrolment", "UNIVERSITY", "CONST100", "UserId", "CASCADE"));
        daoMetadata.insert(new Metadata("CONST500", "UNIVERSITY", "R", "harsha_thesis_api_solution4_entity_Enrolment", "UNIVERSITY", "CONST200", "CourseId", "NODELETE"));
        daoMetadata.insert(new Metadata("CONST600", "UNIVERSITY", "F", "harsha_thesis_api_solution4_entity_Course", "UNIVERSITY", "CONST500", "CourseId", "NODELETE"));
        daoMetadata.insert(new Metadata("CONST700", "UNIVERSITY", "F", "harsha_thesis_api_solution4_entity_User", "UNIVERSITY", "CONST400", "UserId", "CASCADE"));
        daoMetadata.close();


        daoUser = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.User"));
        daoCourse = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.Course"));
        daoEnrolment = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.Enrolment"));


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
        daoUser.insert(user);

        Course course = new Course();
        course.setCourseId(defaultCourse.getCourseId());
        course.setCourseName(defaultCourse.getCourseName());
        course.setLevel(defaultCourse.getLevel());
        course.setTrimister(defaultCourse.getTrimister());
        course.setYear(defaultCourse.getYear());
        daoCourse.insert(course);

        Enrolment enrolment = new Enrolment();
        enrolment.setCourseId(defaultEnrolment.getCourseId());
        enrolment.setRowId(defaultEnrolment.getRowId());
        enrolment.setType(defaultEnrolment.getType());
        enrolment.setUserId(defaultEnrolment.getUserId());
        daoEnrolment.insert(enrolment);

        daoUser.close();
        daoCourse.close();
        daoEnrolment.close();
    }

    public void experiment(int runs) throws Exception {
        users = CommonHelper.GetUserEntities(Solution.FOUR, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.FOUR, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.FOUR, csvFiles[2]);


        daoMetadata = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.entity.Metadata"));
        daoUser = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.User"));
        daoCourse = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.Course"));
        daoEnrolment = new BaseDAO(new ValidationHandler("harsha.thesis.api.solution4.Enrolment"));



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

        daoMetadata.close();
        daoUser.close();
        daoCourse.close();
        daoEnrolment.close();
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
            daoUser.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_user:" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToInsert) {
            daoCourse.insert(entity);
        }
        experiment.stop();
        experiment.log("insert_course:" + experiment.duration() + "\n");

        //Enrolments

        experiment.start();
        for (BaseEntity entity : enrolmentsToInsert) {
            daoEnrolment.insert(entity);
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
            daoCourse.update(entity);

        }
        experiment.stop();
        experiment.log("update_course:" + experiment.duration() + "\n\n");
    }

    private void updateEnrolment() throws Exception {
        Random random = new Random(Main.UPDATE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToUpdate = new ArrayList<Enrolment>(enrolments);
        Collections.shuffle(enrolmentsToUpdate, random);

        experiment.start();
        Iterator<Enrolment> it = enrolments.iterator();
        for (Enrolment entity : enrolmentsToUpdate) {
            entity.setCourseId(it.next().getCourseId());
            daoEnrolment.insert(entity);
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
            daoEnrolment.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_enrolment:" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToDelete) {
            daoCourse.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_course:" + experiment.duration() + "\n");

        //USers
        experiment.start();
        for (BaseEntity entity : usersToDelete) {
            daoUser.delete(entity);
        }
        experiment.stop();
        experiment.log("delete_user:" + experiment.duration() + "\n\n");

    }
}
