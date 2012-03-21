/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.em.EntityManager;
import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS3 extends CommonValidationHandler{

    public ValidationHandlerS3(EntityManager em) {
        super(em);
    }
    
    @Override
    public String solution() {
        return "solution3";
    }
    
    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
