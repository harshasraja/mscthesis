/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api;

import harsha.api.Entity;
import java.util.List;

/**
 *
 * @author jcrada
 */
public interface ValidationHandler {
    
    public String solution();

    
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception;
    
    public List<Entity> retrieveChildren(Entity entity) throws Exception;
    
    
    public void onInsert(Entity entity) throws Exception;

    public void beforeUpdate(Entity entity) throws Exception;

    public void afterUpdate(Entity entity) throws Exception;

    
    public void onDelete(Entity entity) throws Exception;
    
    
}
