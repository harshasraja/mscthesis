package harsha.thesis.exp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.cassandra.utils.Pair;

public class ArtificialData {

    private static ArtificialData INSTANCE;

    public static ArtificialData Instance() {
        if (INSTANCE == null) {
            INSTANCE = new ArtificialData();
        }
        return INSTANCE;
    }
    public static final int INITIAL_STUDENT_ID = 100;
    public static final int INITIAL_COURSE_ID = 100;
    public static final int INITIAL_ENROLMENT_ID = 5000;
    public static final String COURSE_BASE_NAME = "COMP";
    public static final String COURSE_ALTERNATIVE_NAME = "SWEN";
    private int numberOfStudents;
    private int numberOfCourses;
    private int numberOfGroupsInCourses;
    private String[] headerStudent;
    private String[] headerCourse;
    private String[] headerEnrolment;

    public ArtificialData() {
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public String[] getHeaderStudent() {
        return headerStudent;
    }

    public void setHeaderStudent(String[] headerStudent) {
        this.headerStudent = headerStudent;
    }

    public String[] getHeaderCourse() {
        return headerCourse;
    }

    public void setHeaderCourse(String[] headerCourse) {
        this.headerCourse = headerCourse;
    }

    public String[] getHeaderEnrolment() {
        return headerEnrolment;
    }

    public void setHeaderEnrolment(String[] headerEnrolment) {
        this.headerEnrolment = headerEnrolment;
    }

    public int getNumberOfGroupsInCourses() {
        return numberOfGroupsInCourses;
    }

    public void setNumberOfGroupsInCourses(int groupSize) {
        this.numberOfGroupsInCourses = groupSize;
    }

    public void writeAll(Writer w) throws Exception {
        writeStudent(w, ",");
        writeCourse(w, ",");
        writeEnrolment(w, ",");
    }

    public void writeStudent(Writer w, String sep) throws Exception {
        for (int i = 0; i < headerStudent.length; ++i) {
            w.write(headerStudent[i]);
            if (i < headerStudent.length - 1) {
                w.write(sep);
            }
        }
        w.write("\n");
        w.flush();

        Random age = new Random(1l);
        int userId = INITIAL_STUDENT_ID;

        for (int i = 0; i < numberOfStudents; ++i) {
            String record = "";
            for (int j = 0; j < headerStudent.length; ++j) {
                String column = headerStudent[j];
                if ("UserId".equals(column)) {
                    record += userId;
                } else if ("FirstName".equals(column)) {
                    record += "First Name (" + userId + ")";
                } else if ("LastName".equals(column)) {
                    record += "Last Name (" + userId + ")";
                } else if ("Email".equals(column)) {
                    record += "First.Last@email." + userId + ".com";
                } else if ("Age".equals(column)) {
                    record += 18 + age.nextInt(50);
                } else if ("Type".equals(column)) {
                    record += "STUDENT";
                }
                if (j < headerStudent.length - 1) {
                    record += sep;
                }
            }
            ++userId;
            w.write(record + "\n");
            w.flush();
        }

    }

    public void writeCourse(Writer w, String sep) throws Exception {

        for (int i = 0; i < headerCourse.length; ++i) {
            w.write(headerCourse[i]);
            if (i < headerCourse.length - 1) {
                w.write(sep);
            }
        }
        w.write("\n");
        w.flush();



        Random random = new Random(1l);
        int courseId = INITIAL_COURSE_ID;
        for (int i = 0; i < numberOfCourses; ++i) {
            String record = "";
            for (int j = 0; j < headerCourse.length; ++j) {
                String column = headerCourse[j];
                if ("CourseId".equals(column)) {
                    record += COURSE_BASE_NAME + courseId;
                } else if ("CourseName".equals(column)) {
                    record += "Computer Engineering (" + courseId + ")";
                } else if ("Trimister".equals(column)) {
                    record += (1 + random.nextInt(3));
                } else if ("Level".equals(column)) {
                    record += (1 + random.nextInt(4));
                } else if ("Year".equals(column)) {
                    record += (random.nextBoolean() ? 2012 : 2011);
                }
                if (j < headerCourse.length - 1) {
                    record += sep;
                }
            }
            ++courseId;
            w.write(record + "\n");
            w.flush();
        }
    }

    public void writeEnrolment(Writer w, String sep) throws Exception {
        for (int i = 0; i < headerEnrolment.length; ++i) {
            w.write(headerEnrolment[i]);
            if (i < headerEnrolment.length - 1) {
                w.write(sep);
            }
        }
        w.write("\n");
        w.flush();


//        int[] groupedCourses = coursesSortedInGroups();
//        int groupSize = numberOfCourses / numberOfGroupsInCourses;
//        int groupIndex = 0;
//        for (int i = 0; i < numberOfStudents; ++i) {
//            for (int j = 0; j < groupSize; ++j) {
//                String record = "";
//                for (int k = 0; k < headerEnrolment.length; ++k) {
//                    String column = headerEnrolment[k];
//                    if ("UserId".equals(column)) {
//                        record += INITIAL_STUDENT_ID + i;
//                    } else if ("CourseId".equals(column)) {
//                        record += COURSE_BASE_NAME + (groupedCourses[ groupIndex++ % groupedCourses.length]) ;
//                    }
//                    if (k < headerEnrolment.length - 1) {
//                        record += sep;
//                    }
//                }
//                w.write(record + "\n");
//                w.flush();
//            }
//            if (groupIndex % groupedCourses.length==0) groupIndex++;
//            w.write("\n");
//        }

        //Returns a (numberOfCourses / groupSize) overlapping groups of courses.
        // [0..10][5..15][10..20][15..25][20..30]
        List<Pair<Double, Double>> groups = MyMath.Overlap(INITIAL_COURSE_ID,
                INITIAL_COURSE_ID + numberOfCourses, (numberOfCourses / numberOfGroupsInCourses) - 1);
        int userId = INITIAL_STUDENT_ID;
        int rowId = INITIAL_ENROLMENT_ID;
        for (int i = 0; i < numberOfStudents; ++i) {
            for (int j = 0; j < groups.size(); ++j) {
                String record = "";
                for (int k = 0; k < headerEnrolment.length; ++k) {
                    String column = headerEnrolment[k];
                    if ("UserId".equals(column)) {
                        record += userId;
                    } else if ("CourseId".equals(column)) {
                        record += COURSE_BASE_NAME + (groups.get(j).left.intValue()
                                + (i % (groups.size() - 1)));
                    } else if ("RowId".equals(column)){
                        record += rowId++;
                    }else if ("Type".equals(column)){
                        record += "STUDENT";
                    }
                    if (k < headerEnrolment.length - 1) {
                        record += sep;
                    }
                }
                w.write(record + "\n");
                w.flush();
            }
//            w.write("\n");
            ++userId;
        }

    }

//    public int[] coursesSortedInGroups() {
//        int groupSize = numberOfCourses / numberOfGroupsInCourses;
//        int[] result = new int[numberOfGroupsInCourses * groupSize];
//        int skip = numberOfGroupsInCourses;
//
//        int resultIndex = 0;
//        for (int i = 0; i < numberOfGroupsInCourses; ++i) {
//            for (int j = 0; j < groupSize; ++j) {
//                result[resultIndex++] = INITIAL_COURSE_ID + i + (j * skip);
//            }
//        }
//
////        for (int i = 0 ; i < result.length ;++i){
////            System.out.print(result[i] + " ");
////            if ((i + 1)% groupSize == 0){
////                System.out.println("");
////            }
////        }
//        return result;
//    }

    public static void main(String[] args) throws Exception {
        File csv = new File("/tmp/cassandra.csv");
        if (csv.exists()) {
            csv.delete();
        }
        csv.createNewFile();
        BufferedWriter w = new BufferedWriter(new FileWriter(csv));

        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(20);
        ad.setNumberOfCourses(60);
        ad.setNumberOfGroupsInCourses(5);
        ad.setHeaderStudent(new String[]{"UserId", "FirstName", "LastName", "Age"});
        ad.setHeaderCourse(new String[]{"CourseId", "CourseName"});
        ad.setHeaderEnrolment(new String[]{"UserId", "CourseId"});

        ad.writeAll(w);
    }

    public static void failedMain(String[] args) throws Exception {
        ArtificialData ad = new ArtificialData();
        ad.setNumberOfStudents(20);
        ad.setNumberOfCourses(60);
        ad.setNumberOfGroupsInCourses(6);
        ad.setHeaderStudent(new String[]{"UserId", "FirstName", "LastName", "Age"});
        ad.setHeaderCourse(new String[]{"CourseId", "CourseName"});
        ad.setHeaderEnrolment(new String[]{"UserId", "CourseId"});

//        ad.coursesSortedInGroups();
    }
}
