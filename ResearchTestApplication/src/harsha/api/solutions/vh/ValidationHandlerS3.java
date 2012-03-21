/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.EntityManager;
import harsha.api.Constraint;
import harsha.api.Entity;
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
        return em.query(Constraint.class, "EntityName", 
                EntityManager.Expression.EQUALS, entity.getClass().getName());
    }

    
    
}
