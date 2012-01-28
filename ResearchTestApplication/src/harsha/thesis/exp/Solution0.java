/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.api.connection.hector.HectorConnectionObject;
import harsha.thesis.api.solution0.dao.BaseDAO;
import harsha.thesis.api.solution0.entity.BaseEntity;
import harsha.thesis.api.solution0.entity.Course;
import harsha.thesis.api.solution0.entity.Enrolment;
import harsha.thesis.api.solution0.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jcrada
 */
public class Solution0 {

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

    public void experiment(int runs) throws Exception {
        users = CommonHelper.GetUserEntities(Solution.ZERO, csvFiles[0]);
        courses = CommonHelper.GetCourseEntities(Solution.ZERO, csvFiles[1]);
        enrolments = CommonHelper.GetEnrolmentEntities(Solution.ZERO, csvFiles[2]);

        dao = new BaseDAO(HectorConnectionObject.class.getName(), Main.HECTOR_CONNECTION);

        for (int i = 0; i < runs; ++i) {
            experiment.log("#RUN: " + (i + 1));
            insert();
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

    private void update() {
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
