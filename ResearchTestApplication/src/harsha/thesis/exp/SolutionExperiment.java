/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package harsha.thesis.exp;

/**
 *
 * @author subramhars
 */
public interface SolutionExperiment {

    public void initialize() throws Exception;
    public void experiment(int runs ) throws Exception;

}
