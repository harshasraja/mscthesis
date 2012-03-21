/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.em;

import harsha.thesis.api.solution.vh.ValidationHandlerS3;

/**
 *
 * @author jcrada
 */
public class EntityManagerS3 extends EntityManager{

    public EntityManagerS3() {
        super();
        setValidationHandler(new ValidationHandlerS3(this));
    }
    
}
