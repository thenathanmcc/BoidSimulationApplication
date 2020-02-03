import java.awt.*;
import java.util.LinkedList;

/** Flock class
 * @author Nathan McCulloch
 */


public class Flock {
    private LinkedList<Boid> boids;

    public Flock() {
        boids = new LinkedList<>();
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
    public LinkedList<Boid> getBoids() {
        return boids;
    }
}
