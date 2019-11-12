import java.util.LinkedList;
import java.util.Random;

/**
 *  Boid.java
 * @author Nathan McCulloch
 *
 */


public class Boid {
    private Vec2 location;
    private Vec2 velocity;
    private Vec2 acceleration;
    private double radius; // radius of the boid when drawn
    private double maxForce;
    private double maxSpeed;
    private boolean dead = false;
    private Random generator = new Random(); // used for generating random numbers

    public Boid(double x, double y) {
        this.acceleration = new Vec2(0, 0);
        double angle = generator.nextDouble();
        velocity = new Vec2(Math.cos(angle), Math.sin(angle));
        location = new Vec2(x, y);
        radius = 10.0;
        maxSpeed = 2;
        maxForce = 0.03;
    }

    public void run(LinkedList<Boid> boids){
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

    public void flock(LinkedList<Boid> boids) {
        Vec2 sep = separate(boids);
        Vec2 ali = align(boids);
        Vec2 coh = cohesion(boids);

        sep.scaleVec(3.5);
        ali.scaleVec(1.5);
        coh.scaleVec(1.0);

        applyForce(sep);
        applyForce(ali);
        applyForce(coh);
    }

    public void update() {
        velocity = velocity.addVec(acceleration);
        velocity.limit(maxSpeed);
        location = location.addVec(velocity);
    }
}
