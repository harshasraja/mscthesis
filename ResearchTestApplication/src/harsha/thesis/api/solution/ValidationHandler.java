/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution;

import harsha.thesis.api.solution.entity.Entity;
import harsha.thesis.api.solution.entity.Metadata;
import java.util.List;

/**
 *
 * @author jcrada
 */
public interface ValidationHandler {
    
    public List<Metadata> retrieveMetadata() throws Exception;
    
    public void onInsert(Entity entity) throws Exception;
    
    public void onUpdate(Entity entity) throws Exception;
    
    public void onDelete(Entity entity) throws Exception;
    
    
}
