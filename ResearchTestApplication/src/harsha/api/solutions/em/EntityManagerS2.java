/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.EntityManager;
import harsha.api.solutions.vh.ValidationHandlerS2;

/**
 *
 * @author jcrada
 */
public class EntityManagerS2 extends EntityManager {

    public EntityManagerS2() {
        super();
        setValidationHandler(new ValidationHandlerS2(this));
    }
}
