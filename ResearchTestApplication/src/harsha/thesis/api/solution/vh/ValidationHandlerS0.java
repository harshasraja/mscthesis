/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution.vh;

import harsha.thesis.api.solution.entity.Constraint;
import harsha.thesis.api.solution.entity.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcrada
 */
public class ValidationHandlerS0 implements ValidationHandler{

    @Override
    public void onDelete(Entity entity) throws Exception {
        //do nothing
        return ;
    }

    @Override
    public void onInsert(Entity entity) throws Exception {
        //do nothing
        return ;
    }

    @Override
    public void onUpdate(Entity entity) throws Exception {
        //do nothing
        return ;
    }

    @Override
    public List<Entity> retrieveDependencies(Entity entity) throws Exception {
        return new ArrayList<Entity>();
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return new ArrayList<Constraint>();
    }

    @Override
    public String solution() {
        return "solution0";
    }
    
}
