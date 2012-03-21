/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.em.EntityManager;
import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS4 extends CommonValidationHandler {

    private Map<Class<? extends Entity>, List<Constraint>> cache =
            new HashMap<Class<? extends Entity>, List<Constraint>>();
    private EntityManager emMetadata;

    public ValidationHandlerS4(EntityManager em, EntityManager emMetadata) {
        super(em);
        this.emMetadata = emMetadata;
    }

    @Override
    public String solution() {
        return "solution4";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        List<Constraint> metadata = cache.get(entity.getClass());
        if (metadata != null) {
            return metadata;
        }
        metadata = new ArrayList<Constraint>();
        cache.put(entity.getClass(), metadata);

        //fill metadata using emMetadata; 


        return metadata;
    }
}
