/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.em;

import harsha.thesis.api.connection.CloudConnector;
import harsha.thesis.api.solution.vh.ValidationHandlerS4;
import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class EntityManagerS4 extends EntityManager {

    private EntityManager emMetadata;

    public EntityManagerS4() {
        super();
        emMetadata = new EntityManager(CloudConnector.getMetadataConnection());
        validationHandler = new ValidationHandlerS4(this, emMetadata);
    }

    @Override
    public Entity find(Class<? extends Entity> clazz, String id) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.find(clazz, id);
        }
        return super.find(clazz, id);
    }

    @Override
    public List<Entity> query(Class<? extends Entity> clazz, String columnName,
            String expression, String columnValue) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.query(clazz, columnName, expression, columnValue);
        }
        return super.query(clazz, columnName, expression, columnValue);
    }

    @Override
    public List<Entity> read(Class<? extends Entity> clazz) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.read(clazz);
        }
        return super.read(clazz);
    }

    @Override
    public List<Entity> read(Class<? extends Entity> clazz, int number) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.read(clazz, number);
        }
        return super.read(clazz, number);
    }

    @Override
    public void insert(Entity entity) throws Exception {
        if (entity.getClass().equals(Constraint.class)) {
            emMetadata.insert(entity);
        } else {
            super.insert(entity);
        }
    }

    @Override
    public void update(Entity entity) throws Exception {
        if (entity.getClass().equals(Constraint.class)) {
            emMetadata.update(entity);
        } else {
            super.update(entity);
        }
    }

    @Override
    public void delete(Entity entity) throws Exception {
        if (entity.getClass().equals(Constraint.class)) {
            emMetadata.delete(entity);
        } else {
            super.delete(entity);
        }
    }
}
