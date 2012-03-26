/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class ValidationHandlerS3 extends MetadataAsEntity {

    public ValidationHandlerS3(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution3";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return em.query(Constraint.class, "ColumnFamily",
                EntityManager.Expression.EQUALS, entity.getClass().getName());
    }

    @Override
    public Constraint findConstraint(String rConstraintName) throws Exception {
        return em.find(Constraint.class, rConstraintName);
    }
}
