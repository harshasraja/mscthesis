/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.em;

import harsha.api.EntityManager;
import harsha.api.connection.CloudConnector;
import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.solutions.vh.ValidationHandlerS4;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class EntityManagerS4 extends EntityManager {

    private EntityManager emMetadata;

    public EntityManagerS4() {
        super();
        validationHandler = new ValidationHandlerS4(this);
        
        emMetadata = new EntityManager(CloudConnector.getMetadataConnection());
        emMetadata.setValidationHandler(validationHandler);
    }

    @Override
    public <T extends Entity> T find(Class<T> clazz, String id) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.find(clazz, id);
        }
        return super.find(clazz, id);
    }

    @Override
    public <T extends Entity> List<T> query(Class<T> clazz, String columnName,
            Expression expression, String columnValue) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.query(clazz, columnName, expression, columnValue);
        }
        return super.query(clazz, columnName, expression, columnValue);
    }

    @Override
    public <T extends Entity> List<T> read(Class<T> clazz) throws Exception {
        if (clazz.equals(Constraint.class)) {
            return emMetadata.read(clazz);
        }
        return super.read(clazz);
    }

    @Override
    public <T extends Entity> List<T> read(Class<T> clazz, int number) throws Exception {
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

    @Override
    public void createColumFamily(Class<? extends Entity> clazz, boolean dropIfExists) throws Exception {
        if (clazz.equals(Constraint.class)) {
            emMetadata.createColumFamily(clazz, dropIfExists);
        } else {
            super.createColumFamily(clazz, dropIfExists);
        }
    }
}
