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
import harsha.api.solutions.em.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author harshasraja
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
        if (supportsMetadata) {
            columnFamilies.add(Constraint.class);
        }
        for (Class<? extends Entity> columnFamily : columnFamilies) {
            try {
                em.createColumFamily(columnFamily, true);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.debug("ERROR in CF: " + columnFamily);
            }
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

    public void updatingPrimaryKeys() throws Exception {
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

    public static void main2(String[] args) throws Exception {
        EntityManager em = new EntityManagerS2();
        em.createColumFamily(Course.class);
        CloudConnector.shutdown();
    }

    public static void main(String[] args) throws Exception {
        Logger.getRootLogger().setLevel(Level.DEBUG);
        EntityManager[] ems = {
            new EntityManagerS0(),
            new EntityManagerS1(),
            new EntityManagerS2(),
            new EntityManagerS3(),
            new EntityManagerS4()
        };
        boolean dropIfExists = false;
        for (EntityManager em : ems) {
            em.createColumFamily(Student.class, dropIfExists);
            em.createColumFamily(Course.class, dropIfExists);
            em.createColumFamily(Enrolment.class, dropIfExists);
        }

        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(10);
        ad.setNumberOfCourses(10);
        ad.setNumberOfCoursesPerStudent(1);

        List<Student> students = ad.generateStudents();
        List<Course> courses = ad.generateCourses();
        List<Enrolment> enrolments = ad.generateEnrolment();

        //INSERT
        for (EntityManager em : ems) {

            for (Student entity : students) {
                em.insert(entity);
            }

            for (Course entity : courses) {
                em.insert(entity);
            }

            for (Enrolment entity : enrolments) {
                em.insert(entity);
            }

        }

        //FIND
        for (EntityManager em : ems) {
            for (Student entity : students) {
                Student e = em.find(Student.class, entity.getStudentId());
                if (e == null) {
                    throw new Exception("Entity not found: " + entity);
                }
            }
            Entity dummy = em.find(Student.class, "DUMMY");
            if (dummy != null) {
                throw new Exception("Non existing entity is not null");
            }

            for (Course entity : courses) {
                Course e = em.find(Course.class, entity.getCourseId());
                if (e == null) {
                    throw new Exception("Entity not found: " + entity);
                }
            }
            dummy = em.find(Course.class, "DUMMY");
            if (dummy != null) {
                throw new Exception("Non existing entity is not null");
            }


            for (Enrolment entity : enrolments) {
                Enrolment e = em.find(Enrolment.class, entity.getRowId());
                if (e == null) {
                    throw new Exception("Entity not found: " + entity);
                }
            }
            dummy = em.find(Enrolment.class, "DUMMY");
            if (dummy != null) {
                throw new Exception("Non existing entity is not null");
            }


        }




        //READ
        Collections.sort(students);
        Collections.sort(courses);
        Collections.sort(enrolments);
        for (EntityManager em : ems) {
            List<Student> queryStudents = em.read(Student.class);
            Collections.sort(queryStudents);
            if (queryStudents.size() != students.size()) {
                throw new Exception("Error reading whole ColumnFamilies");
            }

            for (int i = 0; i < queryStudents.size(); ++i) {
                if (!queryStudents.get(i).getStudentId().equals(students.get(i).getStudentId())) {
                    throw new Exception("Error reading whole ColumnFamily" + em.columnFamily(Student.class));
                }
            }

            List<Course> queryCourse = em.read(Course.class);
            Collections.sort(queryCourse);
            if (queryCourse.size() != courses.size()) {
                throw new Exception("Error reading whole ColumnFamily" + em.columnFamily(Course.class));
            }

            for (int i = 0; i < queryCourse.size(); ++i) {
                if (!queryCourse.get(i).getCourseId().equals(courses.get(i).getCourseId())) {
                    throw new Exception("Error reading whole ColumnFamily" + em.columnFamily(Course.class));
                }
            }

            List<Enrolment> queryEnrolment = em.read(Enrolment.class);
            Collections.sort(queryEnrolment);
            if (queryEnrolment.size() != enrolments.size()) {
                throw new Exception("Error reading whole ColumnFamily" + em.columnFamily(Enrolment.class));
            }

            for (int i = 0; i < queryEnrolment.size(); ++i) {
                if (!queryEnrolment.get(i).getRowId().equals(enrolments.get(i).getRowId())) {
                    throw new Exception("Error reading whole ColumnFamily" + em.columnFamily(Enrolment.class));
                }
            }
        }


        //QUERY


        for (EntityManager em : ems) {
            for (Student entity : students) {

                List<Student> e = em.query(Student.class, "StudentId", EntityManager.Expression.EQUALS,
                        entity.getStudentId());
                if (e.size() != 1) {
                    log.debug(e.size() + " entities found");
                    for (Student result : e) {
                        log.debug(result);
                    }
                    throw new Exception("Query Entity not found: " + entity);
                }
            }

            for (Course entity : courses) {
                List<Course> e = em.query(Course.class, "CourseId", EntityManager.Expression.EQUALS,
                        entity.getCourseId());
                if (e.size() != 1) {
                    throw new Exception("Query Entity not found: " + entity);
                }
            }

            for (Enrolment entity : enrolments) {
                List<Enrolment> e = em.query(Enrolment.class, "RowId", EntityManager.Expression.EQUALS,
                        entity.getRowId());
                if (e.size() != 1) {
                    throw new Exception("Query Entity not found: " + entity);
                }
            }
        }


//UPDATE
        //        for (EntityManager em : ems) {
//
//            for (Student student : students) {
//                student.setFirstName("UPDATED-" + student.getStudentId());
//                em.update(student);
//            }
//
//            for (Course course : courses) {
//                course.setCourseName("SWEN" + course.getCourseName().substring(4));
//                em.update(course);
//            }
//
//            for (Enrolment enrolment : enrolments) {
//                enrolment.setType("UPDATED");
//                em.update(enrolment);
//            }
//        }

//DELETE
//        for (EntityManager em : ems) {
//
//            for (Student student : students) {
//                em.delete(student);
//            }
//
//            for (Course course : courses) {
//                em.delete(course);
//            }
//
//            for (Enrolment enrolment : enrolments) {
//                em.delete(enrolment);
//            }
//
//        }





        CloudConnector.shutdown();
    }
}
