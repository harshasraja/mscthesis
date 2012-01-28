/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

/**
 *
 * @author jcrada
 */
    
    public enum Solution {

        ZERO, ONE, TWO, THREE, FOUR;
        public static final String ENTITY_BASENAME = "harsha.thesis.api.solution.entity";
        public static final String HELPER_BASENAME = "harsha.thesis.api.solution.helper";

        public int number() {
            switch (this) {
                case ZERO:
                    return 0;
                case ONE:
                    return 1;
                case TWO:
                    return 2;
                case THREE:
                    return 3;
                case FOUR:
                    return 4;
                default:
                    throw new AssertionError();
            }
        }

        public Class userEntityClass() {
            String className = ENTITY_BASENAME.replace("solution", "solution" + number()) + ".User";
            Class result = null;
            try {
                result = Class.forName(className);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return result;
        }

        public Class courseEntityClass() {
            String className = ENTITY_BASENAME.replace("solution", "solution" + number()) + ".Course";
            Class result = null;
            try {
                result = Class.forName(className);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return result;
        }

        public Class enrolmentEntityClass() {
            String className = ENTITY_BASENAME.replace("solution", "solution" + number()) + ".Enrolment";
            Class result = null;
            try {
                result = Class.forName(className);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return result;
        }

        public Class helperClass() {
            String className = HELPER_BASENAME.replace("solution", "solution" + number()) + ".CSVReader";
            Class result = null;
            try {
                result = Class.forName(className);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return result;
        }
    }
    
    

