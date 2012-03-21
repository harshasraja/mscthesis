/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.entity.Entity;
import harsha.thesis.api.solution.entity.Constraint;
import java.util.List;

/**
 *
 * @author jcrada
 */
public interface ValidationHandler {
    
    public String solution();
    
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception;
    
    public List<Entity> retrieveDependencies(Entity entity) throws Exception;
    
    public void onInsert(Entity entity) throws Exception;
    
    public void onUpdate(Entity entity) throws Exception;
    
    public void onDelete(Entity entity) throws Exception;
    
    
}
