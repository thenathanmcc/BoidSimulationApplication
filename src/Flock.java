import java.awt.*;
import java.util.ArrayList;

/** Flock class
 * @author Nathan McCulloch
 */


public class Flock {
    private ArrayList<Boid> boids;

    public Flock() {
        boids = new ArrayList<>();
    }

    /**
     * Run flocking algorithm
     */
    public void run() {
        for (Boid boid : boids) {
            boid.run(boids);
        }
    }

    /**
     * Add Boid to simulation
     * @param boid new boid
     */
    public void addBoid(Boid boid) {
        boids.add(boid);
    }

    /**
     * Display each boid
     * @param g graphics
     */
    public void display(Graphics g) {
        for (Boid boid : boids) {
            boid.display(g);
        }
    }

    /**
     * Fetch list of boids currently in the simulation
     * @return linked list of boids
     */
    public ArrayList<Boid> getBoids() {
        return boids;
    }
}
