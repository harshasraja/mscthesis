/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.Entity;
import harsha.api.EntityManager;
import harsha.api.solutions.vh.ValidationHandlerS2;
import java.util.List;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 *
 * @author harshasraja
 */
public class EntityManagerS2 extends EntityManager {

    public EntityManagerS2() {
        super();
        setValidationHandler(new ValidationHandlerS2(this));
    }

    @Override
    public void insert(Entity entity) throws Exception {
        String primaryKeyColumn = Entity.GetPrimaryKeyColumn(entity.getClass());
        String primaryKeyValue = Entity.GetValue(primaryKeyColumn, entity);

        if (!"-1".equals(primaryKeyValue)) {
            super.insert(entity);
        } else {
            //if is metadata
            String[] columnNames = {
                Entity.GetPrimaryKeyColumn(entity.getClass()),
                "Metadata"
            };

            Mutator<String> mutator = getConnection().getMutator();
            for (String column : columnNames) {
                mutator.addInsertion(
                        Entity.GetValue(Entity.GetPrimaryKeyColumn(entity.getClass()), entity),
                        columnFamily(entity.getClass()),
                        HFactory.createStringColumn(column, Entity.GetValue(column, entity)));
            }
            mutator.execute();
        }
    }
}
