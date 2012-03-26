/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.EntityManager;
import harsha.api.Entity;
import harsha.api.solutions.vh.ValidationHandlerS1;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class EntityManagerS1 extends EntityManager {

    public EntityManagerS1() {
        super();
        setValidationHandler(new ValidationHandlerS1(this));
    }

    @Override
    public List<String> getColumnsFor(Class<? extends Entity> clazz) {
        return Entity.GetAllColumnsFor(clazz);
    }
}
