/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import harsha.thesis.exp.entity.Course;
import harsha.thesis.exp.entity.User;
import harsha.thesis.exp.entity.Enrolment;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class CommonHelper {

    public static User GetDefaultUser() {
        return new User("10", "Default", "Default", "default@default.com", "10", "STUDENT");
    }

    public static Course getDefaultCourse() {
        return new Course("10", "Default", "0", "0", "2000");
    }

    public static Enrolment getDefaultEnrolment() {
        return new Enrolment("10", "10", "10", "STUDENT");
    }

    public static List GetUserEntities(Solution solution, String csvFile) throws Exception {
        switch (solution) {
            case ZERO:
                harsha.thesis.api.solution0.helper.CSVReader csvReader0 =
                        new harsha.thesis.api.solution0.helper.CSVReader();
                return csvReader0.getEntities(csvFile, solution.userEntityClass().getName());
            case ONE:
                harsha.thesis.api.solution1.helper.CSVReader csvReader1 =
                        new harsha.thesis.api.solution1.helper.CSVReader();
                return csvReader1.getEntities(csvFile, solution.userEntityClass().getName());
            case TWO:
                harsha.thesis.api.solution2.helper.CSVReader csvReader2 =
                        new harsha.thesis.api.solution2.helper.CSVReader();
                return csvReader2.getEntities(csvFile, solution.userEntityClass().getName());
            case THREE:
                harsha.thesis.api.solution3.helper.CSVReader csvReader3 =
                        new harsha.thesis.api.solution3.helper.CSVReader();
                return csvReader3.getEntities(csvFile, solution.userEntityClass().getName());
            case FOUR:
                harsha.thesis.api.solution4.helper.CSVReader csvReader4 =
                        new harsha.thesis.api.solution4.helper.CSVReader();
                return csvReader4.getEntities(csvFile, solution.userEntityClass().getName());
            default:
                throw new AssertionError();
        }
    }

    public static List GetCourseEntities(Solution solution, String csvFile) throws Exception {
        switch (solution) {
            case ZERO:
                harsha.thesis.api.solution0.helper.CSVReader csvReader0 =
                        new harsha.thesis.api.solution0.helper.CSVReader();
                return csvReader0.getEntities(csvFile, solution.courseEntityClass().getName());
            case ONE:
                harsha.thesis.api.solution1.helper.CSVReader csvReader1 =
                        new harsha.thesis.api.solution1.helper.CSVReader();
                return csvReader1.getEntities(csvFile, solution.courseEntityClass().getName());
            case TWO:
                harsha.thesis.api.solution2.helper.CSVReader csvReader2 =
                        new harsha.thesis.api.solution2.helper.CSVReader();
                return csvReader2.getEntities(csvFile, solution.courseEntityClass().getName());
            case THREE:
                harsha.thesis.api.solution3.helper.CSVReader csvReader3 =
                        new harsha.thesis.api.solution3.helper.CSVReader();
                return csvReader3.getEntities(csvFile, solution.courseEntityClass().getName());
            case FOUR:
                harsha.thesis.api.solution4.helper.CSVReader csvReader4 =
                        new harsha.thesis.api.solution4.helper.CSVReader();
                return csvReader4.getEntities(csvFile, solution.courseEntityClass().getName());
            default:
                throw new AssertionError();
        }
    }

    public static List GetEnrolmentEntities(Solution solution, String csvFile) throws Exception {
        switch (solution) {
            case ZERO:
                harsha.thesis.api.solution0.helper.CSVReader csvReader0 =
                        new harsha.thesis.api.solution0.helper.CSVReader();
                return csvReader0.getEntities(csvFile, solution.enrolmentEntityClass().getName());
            case ONE:
                harsha.thesis.api.solution1.helper.CSVReader csvReader1 =
                        new harsha.thesis.api.solution1.helper.CSVReader();
                return csvReader1.getEntities(csvFile, solution.enrolmentEntityClass().getName());
            case TWO:
                harsha.thesis.api.solution2.helper.CSVReader csvReader2 =
                        new harsha.thesis.api.solution2.helper.CSVReader();
                return csvReader2.getEntities(csvFile, solution.enrolmentEntityClass().getName());
            case THREE:
                harsha.thesis.api.solution3.helper.CSVReader csvReader3 =
                        new harsha.thesis.api.solution3.helper.CSVReader();
                return csvReader3.getEntities(csvFile, solution.enrolmentEntityClass().getName());
            case FOUR:
                harsha.thesis.api.solution4.helper.CSVReader csvReader4 =
                        new harsha.thesis.api.solution4.helper.CSVReader();
                return csvReader4.getEntities(csvFile, solution.enrolmentEntityClass().getName());
            default:
                throw new AssertionError();
        }
    }
}
