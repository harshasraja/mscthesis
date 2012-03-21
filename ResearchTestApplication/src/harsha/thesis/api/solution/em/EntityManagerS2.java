/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.em;

import harsha.thesis.api.solution.entity.Entity;
import harsha.thesis.api.solution.vh.ValidationHandlerS2;

/**
 *
 * @author jcrada
 */
public class EntityManagerS2 extends EntityManager{

    
    public EntityManagerS2(){
        super();
        setValidationHandler(new ValidationHandlerS2(this));
    }
    
    @Override
    public void delete(Entity entity) throws Exception {
        super.delete(entity);
    }

   
    @Override
    public void insert(Entity entity) throws Exception {
        super.insert(entity);
    }

    @Override
    public void update(Entity entity) throws Exception {
        super.update(entity);
    }
    
}
