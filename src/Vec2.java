/**
 * Vec2.java
 * My own vector class
 * @author Nathan McCulloch
 */
import java.util.ArrayList;
import java.math.*;

public class Vec2 {
    private double x, y;

    public Vec2(){
        x = 0.0;
        y = 0.0;
    }

    public Vec2(Vec2 vec) {
        deepCopyVec(vec);
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x-coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     *
     * @return y-coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     *
     * @param x x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @param y y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @return vector as a string
     */
    public String toString() {
        return "( " + x + ", " + y + " )";
    }

    /**
     * Deep copies a vector
     * @param vec vector to be copied
     * @return new copied vector
     */
    public Vec2 deepCopyVec( Vec2 vec) {
        Vec2 newVec = new Vec2();
        x = vec.getX();
        y = vec.getY();
        return newVec;
    }

    /**
     * Adds a vector to this vector
     * @param vec vector to be added
     * @return new vector
     */
    public Vec2 addVec(Vec2 vec) {
        return new Vec2( this.getX() + vec.getX(), this.getY() + vec.getY());
    }

    /**
     * Subtracts a vector from this vector
     * @param vec vector to be subtracted
     * @return new vector
     */
    public Vec2 subVec(Vec2 vec){
        return new Vec2( this.getX() - vec.getX(), this.getY() - vec.getY());
    }

    /**
     * Calculates the magnitude(length) of the vector
     * @return the magnitude of the vector
     */
    public double getMagnitude() {
        return Math.pow(x*x + y*y, 0.5);
    }

    /**
     * Scales the vector by a factor
     * @param k factor to scale by
     */
    public void scaleVec(double k){
        x = x*k;
        y = y*k;
    }

    /**
     * Normalizes the vector i.e changes the magnitude of the vector to 1
     */
    public void normalize() {
        double mag = getMagnitude();
        x = x/mag;
        y = y/mag;
    }

    /**
     * Normalizes a Vector
     * @param vec vector to be normalized
     * @return normalized vector
     */
    public static Vec2 normalize(Vec2 vec) {
        double mag = vec.getMagnitude();
        double x = vec.getX()/mag;
        double y = vec.getY()/mag;
        return new Vec2(x, y);
    }

    /**
     * Calculates the dot product between this vector and another
     * @param vec other vector
     * @return dot product
     */
    public double dotProduct(Vec2 vec) {
        return this.getX() * vec.getX() + this.getY() * vec.getY();
    }

    /**
     * Calculates the cross product between this vector and another
     * @param vec other vector
     * @return cross product
     */
    public  double crossProduct(Vec2 vec){
        return this.getX()*vec.getY() - this.getY()*vec.getX();
    }

    /**
     * Calculates the angle between this vector and another
     * @param vec other vector
     * @return angle between the two vectors
     */
    public double angleBetweenVec(Vec2 vec) {
        double crossProduct = this.crossProduct(vec);
        Vec2 normalizedVec1 = normalize(this);
        Vec2 normalizedVec2 = normalize(vec);
        double product = normalizedVec1.dotProduct(normalizedVec2);
        if (product > 1.0) {
            product = 1.0;
        } else if (product < -1.0) {
            product = 1.0;
        }

        if (crossProduct < 0) {
            return 2 * Math.PI - Math.acos(product);
        } else {
            return Math.acos(product);
        }
    }

    /**
     * Limits the magnitude(length) of a vector
     * @param limit limit of magnitude of a vector
     */
    public void limit(double limit) {
        double mag = getMagnitude();
        if (mag > limit) {
            scaleVec(1.0/(mag - limit));
        }
    }
}
