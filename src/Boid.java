import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Boid.java
 * @author Nathan McCulloch
 * Code adapted from https://p5js.org/examples/simulate-flocking.html
 */


public class Boid {
    public Vec2 location;
    private Vec2 velocity;
    private Vec2 acceleration;
    public double radius; // radius of the boid when drawn
    private double maxForce;
    private double maxSpeed;
    private Player player;
    public boolean dead = false;
    private Random generator = new Random(); // used for generating random numbers

    public Boid(double x, double y, Player player) {
        this.acceleration = new Vec2(0, 0);
        double angle = generator.nextDouble();
        velocity = new Vec2(Math.cos(angle), Math.sin(angle));
        location = new Vec2(x, y);
        radius = 10.0;
        maxSpeed = 2;
        maxForce = 0.03;

        this.player = player;
    }

    /**
     * Set the boid to dead i.e boid no longer in play
     */
    public void setDead(){
        dead = true;
    }

    /**
     * Runs the boid simulation
     * @param boids list of boids
     */
    public void run(ArrayList<Boid> boids){
        flock(boids);
        update();
    }

    /**
     * Applies a force to the boid
     * @param force force vector to be applied
     */
    public void applyForce(Vec2 force) {
        acceleration = acceleration.addVec(force);
    }

    /**
     * Applies the flocking algorithm to the Boid
     * @param boids lis of all boids in the simulation
     */
    public void flock(ArrayList<Boid> boids) {
        Vec2 sep = separate(boids);
        Vec2 ali = align(boids);
        Vec2 coh = cohesion(boids);

        sep.scaleVec(3.5);
        ali.scaleVec(1.5);
        coh.scaleVec(1.0);

        applyForce(sep);
        applyForce(ali);
        applyForce(coh);

        Vec2 seekPlayerVec = seek(new Vec2(player.getCentreX(), player.getCentreY()));
        seekPlayerVec.scaleVec(6.0);
        applyForce(seekPlayerVec);
    }

    /**
     * Draw the boid on the display
     * @param g
     */
    public void display(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval((int) location.getX(), (int) location.getY(), (int)radius, (int)radius);
    }

    /**
     * Updates the boid's velocity and location vectors
     */
    public void update() {
        velocity = velocity.addVec(acceleration);
        velocity.limit(maxSpeed);
        location = location.addVec(velocity);
        acceleration.scaleVec(0.0);
    }

    /**
     * Calculates seek vector towards a desired target
     * @param target Target vector to seek
     * @return vector seek vector
     */
    public Vec2 seek(Vec2 target) {
        Vec2 desired = target.subVec(location);
        desired.normalize();
        desired.scaleVec(maxSpeed);
        Vec2 steer = desired.subVec(velocity);
        steer.limit(maxForce);
        return steer;
    }

    /**
     * Calculates the separation vector that should
     * be applied to the boid
     * @param boids List of the boids in the simulation
     * @return separation vector
     */
    public Vec2 separate(ArrayList<Boid> boids) {
        double desired_separation = 25.0;
        Vec2 steer = new Vec2(0,0);
        double dist = 0.0;
        Vec2 difference_vector = new Vec2(0,0);
        int count = 0;
        for (Boid other_boid : boids) {
            // Get distance to other boid
            difference_vector = location.subVec(other_boid.location);
            dist = difference_vector.getMagnitude();
            if (dist < desired_separation) {
                difference_vector.normalize();
                difference_vector.scaleVec(1.0/dist);
                steer.addVec(difference_vector);
                count++;
            }
        }

        if (count > 0)
            steer.scaleVec(1.0/count);

        if (steer.getMagnitude() > 0) {
            steer.normalize();
            steer.scaleVec(maxSpeed);
            steer.subVec(velocity);
            steer.limit(maxForce);
        }

        return steer;
    }

    /**
     * Calculates the alignment vector
     * @param boids list of other boids
     * @return alignment vector
     */
    public Vec2 align(ArrayList<Boid> boids){
        double neighbour_distance = 50; //
        Vec2 sum = new Vec2(0.0, 0.0);
        int count = 0;
        double distance = 0.0;
        for (Boid other_boid : boids) {
            distance = location.subVec(other_boid.location).getMagnitude();
            if ((distance > 0) && (distance < neighbour_distance)) {
                sum = sum.addVec(other_boid.velocity);
                count++;
            }
        }

        if (count > 0) {
            sum.scaleVec(1.0/count);
            sum.normalize();
            sum.scaleVec(maxSpeed);
            Vec2 steer = sum.subVec(velocity);
            steer.limit(maxForce);
            return steer;
        } else {
            return new Vec2(0,0);
        }
    }

    /**
     * Calculates the Cohesion vector
     * @param boids list of boids
     * @return cohesion vector
     */
    public Vec2 cohesion(ArrayList<Boid> boids) {
        double neighbour_distance = 50;
        Vec2 sum = new Vec2(0, 0);
        int count = 0;
        double distance = 0;
        for (Boid other_boid : boids) {
            distance = location.subVec(other_boid.location).getMagnitude();
            if ((distance > 0) && (distance < neighbour_distance)) {
                sum = sum.addVec(other_boid.location);
                count++;
            }
        }

        if (count > 0) {
            sum.scaleVec(1.0/count);
            return seek(sum);
        } else {
            return new Vec2(0, 0);
        }
    }
}
