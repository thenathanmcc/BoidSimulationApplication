/**
 * BoidSimulationApp.java
 *
 * @author Nathan McCulloch
 */

import javax.swing.*;
import java.awt.*;

public class BoidSimulationApp extends JFrame {

    public BoidSimulationApp () {
        super("Boid Simulation");
    }


    private static class displayPanel extends JPanel {
        private int panel_width = 750;
        private int panel_height = 750;

        public displayPanel () {
            setFocusable(true);
            setPreferredSize( new Dimension(panel_width, panel_height));
            setBackground(Color.white);
        }
    }
}
