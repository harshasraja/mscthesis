/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.em;

import harsha.thesis.api.solution.vh.ValidationHandlerS0;

/**
 *
 * @author jcrada
 */
public class EntityManagerS0 extends EntityManager{

    public EntityManagerS0(){
        super();
        setValidationHandler(new ValidationHandlerS0());
    }
    
}
