/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.em;

import harsha.thesis.api.solution.vh.ValidationHandlerS1;
import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;

/**
 *
 * @author jcrada
 */
public class EntityManagerS1 extends EntityManager {

    public EntityManagerS1(){
        super();
        setValidationHandler(new ValidationHandlerS1(this));
    }
    
    @Override
    public void delete(Entity entity) throws Exception {
        if (entity instanceof Constraint) {
            throw new RuntimeException("Cannot delete Metadata in Solution 1");
        }
        super.delete(entity);
    }

    @Override
    public void insert(Entity entity) throws Exception {
        if (entity instanceof Constraint) {
            throw new RuntimeException("Cannot insert Metadata in Solution 1");
        }
        super.insert(entity);
    }

    @Override
    public void update(Entity entity) throws Exception {
        if (entity instanceof Constraint) {
            throw new RuntimeException("Cannot update Metadata in Solution 1");
        }
        super.update(entity);
    }
}
