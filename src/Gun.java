/**
 * Gun Class
 * @author Nathan McCulloch
 */

import java.awt.*;

public class Gun extends Polygon{
    private Polygon originalPolygon;
    private double width = 20.0;
    private double height = 20.0;

    public Gun(double x, double y, double centreX, double centreY) {
        originalPolygon = new Polygon();
        addPoint((int) (x + width), (int) (y - height/2.0));
        addPoint((int) (x + width + 5), (int) (y - height/2.0));
        addPoint((int) (x + width + 5), (int) (y + height/2.0));
        addPoint((int) (x + width), (int) (y + height/2.0));

        originalPolygon.addPoint((int) (x + width),(int) (y - height/2.0));
        originalPolygon.addPoint((int) (x + width + 5),(int) (y - height/2.0));
        originalPolygon.addPoint((int) (x + width + 5),(int) (y + height/2.0));
        originalPolygon.addPoint((int) (x + width), (int) (y + height/2.0));
    }

    /**
     * @return the original polygon
     */
    public Polygon getOriginalPolygon(){
        return originalPolygon;
    }

    /**
     * Get the x-coordinate of the centre of the polygon
     * @return x-coordinate of the centroid
     */
    public double getCentreX(){
        double result = 0.0;
        for (int x : xpoints) {
            result += x;
        }
        return result/xpoints.length;
    }

    /**
     * Get the y-coordinate of the centre of the polygon
     * @return y-coordinate of the centroid
     */
    public double getCentreY(){
        double result = 0.0;
        for (int y : ypoints) {
            result += y;
        }
        return result/ypoints.length;
    }
}