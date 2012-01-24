/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jcrada
 */
public class Pool {

    private static Pool INSTANCE;

    public static Pool Instance() {
        if (INSTANCE == null) {
            INSTANCE = new Pool();
        }
        return INSTANCE;
    }
    private Map<String, Experiment> experiments;

    private Pool() {
        experiments = new HashMap<String, Experiment>();
    }
    
    public void register(Experiment e) {
        experiments.put(e.getCode(), e);
    }
    
    public void deregister(Experiment e) {
        experiments.remove(e.getCode());
    }
    
    public Experiment get(String code){
        return experiments.get(code);
    }
    
    
}
