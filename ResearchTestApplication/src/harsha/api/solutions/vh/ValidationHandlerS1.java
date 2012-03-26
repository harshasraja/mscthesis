/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class ValidationHandlerS1 extends MetadataAsText {

    public ValidationHandlerS1(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution1";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return Constraint.Parse(entity.getMetadata());
    }
}
