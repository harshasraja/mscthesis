/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.api.solution1.dao.BaseDAO;
import harsha.thesis.api.solution1.entity.BaseEntity;
import harsha.thesis.api.solution1.entity.Course;
import harsha.thesis.api.solution1.entity.Enrolment;
import harsha.thesis.api.solution1.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jcrada
 */
public class Solution1 {

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

    public void experiment(int runs) throws Exception {
        users = CommonHelper.GetUserEntities(Solution.ONE, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.ONE, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.ONE, csvFiles[2]);

        dao = new BaseDAO(HectorConnectionObject.class.getName(), Main.HECTOR_CONNECTION);


        for (int i = 0; i < runs; ++i) {
            experiment.log("#RUN: " + (i + 1));
            insert();
            String newCourseId = (i + 1) % 2 == 0 ? ArtificialData.COURSE_BASE_NAME
                    : ArtificialData.COURSE_ALTERNATIVE_NAME;
            updateCourse(newCourseId);
            updateEnrolment();
            delete();
        }

        dao.close();
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
        experiment.log("insert[user](ms):" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert[course](ms):" + experiment.duration() + "\n");

        //Enrolments

        experiment.start();
        for (BaseEntity entity : enrolmentsToInsert) {
            dao.insert(entity);
        }
        experiment.stop();
        experiment.log("insert[enrolment](ms):" + experiment.duration() + "\n\n");
    }

    private void updateCourse(String newBaseKey) throws Exception{
        Random random = new Random(Main.UPDATE_COURSE_RANDOM_SEED);
        List<Course> coursesToUpdate = new ArrayList<Course>(courses);
        Collections.shuffle(coursesToUpdate, random);
        
        experiment.start();
        for (Course entity : coursesToUpdate){
            String keyForUpdate = newBaseKey + entity.getCourseId().substring(newBaseKey.length());
            entity.setKeyForUpdate(keyForUpdate);
            dao.update(entity);
            
        }
        experiment.stop();
        experiment.log("update[course]: " + experiment.duration() + "\n\n");
    }

    private void updateEnrolment() throws Exception{
        Random random = new Random(Main.UPDATE_ENROLMENT_RANDOM_SEED);
        List<Enrolment> enrolmentsToUpdate = new ArrayList<Enrolment>(enrolments);
        Collections.shuffle(enrolmentsToUpdate, random);
        
        experiment.start();
        Iterator<Enrolment> it = enrolments.iterator();
        for (Enrolment entity : enrolmentsToUpdate){
            entity.setCourseId(it.next().getCourseId());
            dao.update(entity);
        }
        experiment.stop();
        experiment.log("update[enrolment]: " + experiment.duration() + "\n\n");
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
        experiment.log("delete[enrolment](ms):" + experiment.duration() + "\n");

        //Courses
        experiment.start();
        for (BaseEntity entity : coursesToDelete) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete[course](ms):" + experiment.duration() + "\n");
        
        //USers
        experiment.start();
        for (BaseEntity entity : usersToDelete) {
            dao.delete(entity);
        }
        experiment.stop();
        experiment.log("delete[user](ms):" + experiment.duration() + "\n\n");

    }
}
