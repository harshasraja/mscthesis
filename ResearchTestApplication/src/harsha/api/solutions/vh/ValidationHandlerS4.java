/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.EntityManager;
import harsha.api.Constraint;
import harsha.api.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author harshasraja
 */
public class ValidationHandlerS4 extends MetadataAsEntity {

    private Map<Class<? extends Entity>, List<Constraint>> cache =
            new HashMap<Class<? extends Entity>, List<Constraint>>();
    private Map<String, Constraint> constraintCache =
            new HashMap<String, Constraint>();

    public ValidationHandlerS4(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution4";
    }

    @Override
    public Constraint findConstraint(String constraintName) throws Exception {
        Constraint result = constraintCache.get(constraintName);
        if (result == null){
            result = em.find(Constraint.class, constraintName);
            constraintCache.put(constraintName, result);
        }
        return result;
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        List<Constraint> metadata = cache.get(entity.getClass());
        if (metadata != null) {
            return metadata;
        }
        metadata = em.query(Constraint.class, "ColumnFamily",
                EntityManager.Expression.EQUALS, entity.getClass().getName());
        cache.put(entity.getClass(), metadata);
        return metadata;
    }
}
