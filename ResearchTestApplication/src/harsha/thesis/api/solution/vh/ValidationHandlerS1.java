/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.em.EntityManager;
import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS1 extends CommonValidationHandler {

    public ValidationHandlerS1(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution1";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        List<Constraint> result = new ArrayList<Constraint>();
        String metadata = entity.getMetadata();
        //do parse.
        return result;
    }
    
    
}
