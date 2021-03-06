/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.api.test.Recorder;
import harsha.thesis.api.solution2.dao.BaseDAO;
import harsha.thesis.api.solution2.entity.BaseEntity;
import harsha.thesis.api.solution2.entity.Course;
import harsha.thesis.api.solution2.entity.Enrolment;
import harsha.thesis.api.solution2.entity.User;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;


/**
 *
 * @author harshasraja
 */
public class Solution2 implements SolutionExperiment {

    private static DecimalFormat DF = new DecimalFormat("# ### ###, ###");
    private Logger log = Logger.getLogger(Solution2.class);
    
    private Recorder experiment;
    private String[] csvFiles;
    //
    private List<User> users;
    private List<Course> courses;
    private List<Enrolment> enrolments;
    private BaseDAO dao;

    public Solution2(Recorder experiment, String[] csvFiles) {
        this.experiment = experiment;
        this.csvFiles = csvFiles;
    }

    public void initialize() throws Exception {
        users = CommonHelper.GetUserEntities(Solution.TWO, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.TWO, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.TWO, csvFiles[2]);

        dao = new BaseDAO();

        Course courseMetadata = new Course();
        courseMetadata.setCourseId("-1");
        courseMetadata.setMetadataStringRepresentation("|ConstraintName:CONST200;"
                + "KeySpace:UNIVERSITY;ConstraintType:P;"
                + "TableName:harsha_thesis_api_solution2_entity_Course;"
                + "RKeySpace:UNIVERSITY;RConstraintName:;"
                + "RColumn:CourseId;DeleteRule:|;|ConstraintName:CONST600;"
                + "KeySpace:UNIVERSITY;ConstraintType:F;"
                + "TableName:harsha_thesis_api_solution2_entity_Enrolment;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST500;"
                + "RColumn:CourseId;DeleteRule:NODELETE|;");

        User userMetadata = new User();
        userMetadata.setUserId("-1");
        userMetadata.setMetadataStringRepresentation("|ConstraintName:CONST100;"
                + "KeySpace:UNIVERSITY;ConstraintType:P;"
                + "TableName:harsha_thesis_api_solution2_entity_User;"
                + "RKeySpace:UNIVERSITY;RConstraintName:;"
                + "RColumn:UserId;DeleteRule:|;"
                + "|ConstraintName:CONST700;KeySpace:UNIVERSITY;ConstraintType:F;"
                + "TableName:harsha_thesis_api_solution2_entity_Enrolment;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST400;"
                + "RColumn:UserId;DeleteRule:CASCADE|;");

        Enrolment enrolmentMetadata = new Enrolment();
        enrolmentMetadata.setRowId("-1");
        enrolmentMetadata.setUserId("-1");
        enrolmentMetadata.setCourseId("-1");
        enrolmentMetadata.setMetadataStringRepresentation("|ConstraintName:CONST300;"
                + "KeySpace:UNIVERSITY;ConstraintType:P;"
                + "TableName:harsha_thesis_api_solution2_entity_Enrolment;"
                + "RKeySpace:UNIVERSITY;RConstraintName:;RColumn:rowId;DeleteRule:|;"
                + "|ConstraintName:CONST400;KeySpace:UNIVERSITY;ConstraintType:R;"
                + "TableName:harsha_thesis_api_solution2_entity_User;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST700;RColumn:UserId;"
                + "DeleteRule:|;|ConstraintName:CONST500;KeySpace:UNIVERSITY;"
                + "ConstraintType:R;TableName:harsha_thesis_api_solution2_entity_Course;"
                + "RKeySpace:UNIVERSITY;RConstraintName:CONST600;RColumn:CourseId;DeleteRule:|;");


        dao.insert(courseMetadata);
        dao.insert(userMetadata);
        dao.insert(enrolmentMetadata);


        dao.close();
    }

    public void experiment(int runs) throws Exception {
        users = CommonHelper.GetUserEntities(Solution.TWO, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.TWO, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.TWO, csvFiles[2]);

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
            dao.update(entity);
        }
        experiment.stop();
        experiment.log("update_user:" + experiment.duration() + "\n\n");
        for (Enrolment entity:  enrolments){
            entity.setUserId("" + (Long.parseLong(entity.getUserId()) * -1));
        }
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
//                log.info(ex);
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
            dao.delete(entity); //Cascaded
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

        
        //Increase Ids
        for (Enrolment enrolment : enrolments) {
            long currentEnrolmentId = Long.parseLong(enrolment.getRowId()) + enrolments.size();
            enrolment.setRowId("" + currentEnrolmentId);
        }

        //Insertion of enrolment for deleting afterwards with user
        for (BaseEntity entity : enrolments) {
            dao.insert(entity);
        }

    }
}
