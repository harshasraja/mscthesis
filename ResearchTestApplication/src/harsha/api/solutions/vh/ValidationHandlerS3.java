/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.EntityManager;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS3 extends MetadataAsEntity{

    public ValidationHandlerS3(EntityManager em) {
        super(em);
    }
    
    @Override
    public String solution() {
        return "solution3";
    }
    
    
    
}
