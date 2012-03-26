/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.ValidationHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harshasraja
 */
public class ValidationHandlerS0 implements ValidationHandler {

    private List<Constraint> metadata = new ArrayList<Constraint>();
    //No referential integrity
    @Override
    public void onDelete(Entity entity) throws Exception {
        //do nothing
    }

    @Override
    public void onInsert(Entity entity) throws Exception {
        //do nothing
    }

    @Override
    public void onUpdate(Entity entity) throws Exception {
        //do nothing
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        return metadata;
    }

    @Override
    public String solution() {
        return "solution0";
    }
}
