/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import java.util.Random;

/**
 *
 * @author jcrada
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Experiment e = new Experiment("Solution0", "", "/tmp");
        e.initialize();

        String allArgs = "";
        for (String s : args) {
            allArgs += s + " ";
        }

        e.logf("#" + allArgs + "\n\n");

        Pool.Instance().register(e);
        for (int i = 0; i < 50; ++i) {
            test(args);
        }
        e.destroy();
    }

    public static void test(String[] args) throws Exception {
        Random random = new Random();

        Experiment e = Pool.Instance().get("Solution0");



        { //Insert
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.logf("Insert: " + (e.duration()) + "\n");
        }

        { //Update
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.logf("Update: " + (e.duration()) + "\n");
        }

        {//Delete
            e.start();
            for (int i = 0; i < 100; ++i) {
//            e.logf("#Inserting " + i + "\n");
            }
            Thread.sleep(random.nextInt(500));
            e.stop();

            e.logf("Delete: " + e.duration() + "\n");
        }
        e.logf("\n");
    }
}
