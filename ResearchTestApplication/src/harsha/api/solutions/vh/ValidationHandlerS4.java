/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.EntityManager;
import harsha.api.Constraint;
import harsha.api.Entity;
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

        metadata =  emMetadata.query(Constraint.class, "ColumnFamily",
                EntityManager.Expression.EQUALS, entity.getClass().getName());
        
        return metadata;
    }
}
