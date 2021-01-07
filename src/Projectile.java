/**
 * Projectile class
 * @author Nathan McCulloch
 */

import java.awt.*;

public class Projectile extends Polygon {
    private double x;
    private double y;
    private double width = 4;
    private double length = 4;
    private Vec2 projectilePoints = new Vec2();
    private double speedX = 5.0;
    private double speedY = 5.0;
    protected Vec2 directionVector;
    private boolean dead = false;

    public Projectile() {

    }

    public Projectile(Vec2 directionVector, double x, double y) {
        //System.out.println("Projectile Direction Vector: " + directionVector);
        this.directionVector = directionVector;
        this.directionVector.scaleVec(-1.0);
        this.speedX *= this.directionVector.getX();
        this.speedY *= this.directionVector.getY();
        this.x = x;
        this.y = y;
        projectilePoints.setX(this.x);
        projectilePoints.setY(this.y);

        addPoint((int)(x), (int)y);
        addPoint((int)(x + width), (int)y);
        addPoint((int)(x + width), (int)(y + length));
        addPoint((int)(x), (int)(y + length));
    }

    /**
     * Draw the projectile on the display
     */
    public void display(Graphics g) {
        g.setColor(Color.red);
        g.fillPolygon(this);
    }

    /**
     * Move the projectile in the direction of the directionVector
     */
    public void move(){
        this.translate((int) speedX, (int) speedY);
    }

    /**
     * @return boolean indicating whether or not the projectile is still in play
     */
    public Boolean isDead() {
        return dead;
    }

    /**
     * Get the x-coordinate of the centre of the polygon
     * @return x-coordinate of the centroid
     */
    public double getCentreX() {
        double result = 0.0;
        for ( int x : xpoints ) {
            result += x;
        }
        return result/xpoints.length;
    }

    /**
     * Get the y-coordinate of the centre of the polygon
     * @return y-coordinate of the centroid
     */
    public double getCentreY() {
        double result = 0.0;
        for ( int y : ypoints ) {
            result += y;
        }
        return result/ypoints.length;
    }

    /**
     * @return the width of the porjectile
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the length of the projectile
     */
    public double getLength() {
        return length;
    }

    /**
     * Set the projectile as dead i.e no longer in play
     */
    public void setDead() {
        dead = true;
    }
}