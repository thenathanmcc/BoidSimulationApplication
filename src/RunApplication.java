/**
 * RunApplication.java
 *
 * @author Nathan McCulloch
 */

import javax.swing.SwingUtilities;

public class RunApplication implements Runnable {

    public void run() {
        BoidSimulationApp application = new BoidSimulationApp();
    }

    public static void main (String args[]) {
        //Start thread for Swing
        SwingUtilities.invokeLater( new RunApplication() );
    }
}
