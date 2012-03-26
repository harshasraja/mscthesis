/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.EntityManager;
import harsha.api.solutions.vh.ValidationHandlerS3;

/**
 *
 * @author harshasraja
 */
public class EntityManagerS3 extends EntityManager{

    public EntityManagerS3() {
        super();
        setValidationHandler(new ValidationHandlerS3(this));
    }
    
}
