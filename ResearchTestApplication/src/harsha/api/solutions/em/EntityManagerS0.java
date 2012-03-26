/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.EntityManager;
import harsha.api.solutions.vh.ValidationHandlerS0;

/**
 *
 * @author harshasraja
 */
public class EntityManagerS0 extends EntityManager{

    public EntityManagerS0(){
        super();
        setValidationHandler(new ValidationHandlerS0());
    }

    

    
}
