/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import harsha.api.example.Course;
import harsha.api.example.Enrolment;
import harsha.api.example.Student;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author jcrada
 */
public class TestEntityManagers {

    private static final Logger log = Logger.getLogger(TestEntityManagers.class);
    private EntityManager em;
    private boolean supportsMetadata;

    public TestEntityManagers(EntityManager em, boolean supportsMetadata) {
        this.em = em;
        this.supportsMetadata = supportsMetadata;
    }

    public List<Entity> entities() {
        List<Entity> result = new ArrayList<Entity>();

        result.add(new Student("10", "Default", "Default", "default@default.com",
                "10", "STUDENT"));
        result.add(new Course("10", "Default 10", "0", "0", "2000"));
        result.add(new Course("11", "Default 11", "1", "1", "2011"));
        result.add(new Enrolment("10", "10", "10", "STUDENT"));
        if (supportsMetadata) {
            result.add(new Constraint("10", "UNIVERSITY", "?", "entity", "?", "", "", "NODELETE"));
        }

        return result;
    }

    public void createColumnFamilies() throws Exception {
        List<Class<? extends Entity>> columnFamilies =
                new ArrayList<Class<? extends Entity>>();
        columnFamilies.add(Student.class);
        columnFamilies.add(Course.class);
        columnFamilies.add(Enrolment.class);

        for (Class<? extends Entity> columnFamily : columnFamilies) {
            em.createColumFamily(columnFamily);
        }
    }

    public void insert() throws Exception {
        log.debug("Inserting entities");
        for (Entity entity : entities()) {
            em.insert(entity);
        }
    }

    public List<Entity> read() throws Exception {
        log.debug("Reading entities");
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(em.find(Student.class, "10"));
        entities.add(em.find(Course.class, "10"));
        entities.add(em.find(Enrolment.class, "10"));
        if (supportsMetadata) {
            entities.add(em.find(Constraint.class, "10"));
        }

        for (Entity entity : entities) {
            log.info(entity);
        }
        entities.clear();
        entities.addAll(em.query(Student.class, "StudentId", EntityManager.Expression.EQUALS, "10"));
        entities.addAll(em.query(Course.class, "CourseId", EntityManager.Expression.EQUALS, "10"));
        entities.addAll(em.query(Enrolment.class, "RowId", EntityManager.Expression.EQUALS, "10"));
        if (supportsMetadata) {
            entities.addAll(em.query(Constraint.class, "ConstraintName", EntityManager.Expression.EQUALS, "10"));
        }
        return entities;
    }

    public void updateAttributes() throws Exception {
        log.debug("Updating attributes");
        List<Entity> entities = read();
        for (Entity entity : entities) {
            for (String column : em.getColumnsFor(entity.getClass())) {
                if (column.contains("Id")) {
                    continue;
                }
                String value = Entity.GetValue(column, entity);
                Entity.SetValue(column, value + " updated", entity);
            }
            em.update(entity);
        }
    }

    public void updatingForeignKeys() throws Exception {
        log.debug("Updating foreign keys");
        List<Entity> entities = read();
        for (Entity entity : entities) {
            if (entity instanceof Enrolment) {
                Enrolment e = (Enrolment) entity;
                e.setCourseId("11");
                em.update(entity);
            }
        }
    }
    
    public void updatingPrimaryKeys() throws Exception{
        log.debug("Updating primary keys");
        List<Entity> entities = read();
        for (Entity entity : entities) {
            if (entity instanceof Student) {
                Student e = (Student) entity;
                e.setStudentId("11");
                em.update(entity);
            }
        }
    }
    
    
}
