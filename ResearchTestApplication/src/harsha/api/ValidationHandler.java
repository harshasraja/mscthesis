/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api;

import java.util.List;

/**
 *
 * @author harshasraja
 */
public interface ValidationHandler {
    
    public String solution();

    
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception;
    

    public void onInsert(Entity entity) throws Exception;

    public void onUpdate(Entity entity) throws Exception;
    
    public void onDelete(Entity entity) throws Exception;
    
    
}
