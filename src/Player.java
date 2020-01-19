import java.awt.*;

public class Player {
    private double x_coordinate;
    private double y_coordinate;
    private double player_width;
    private double player_height;
    private Polygon original_player_polygon; // Need to store original polygon for rotation
    private Polygon current_player_polygon;
    private double speed = 4;
    private Vec2 current_player_direction = new Vec2(0.0, 1.0);
    private final Vec2 initial_player_direction = new Vec2(0.0, 1.0);


    public Player(double x_coordinate, double y_coordinate, double height, double width){
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.player_width = width;
        this.player_height = height;

        current_player_polygon = new Polygon();
        original_player_polygon = new Polygon();

        current_player_polygon.addPoint((int)x_coordinate, (int)y_coordinate);
        current_player_polygon.addPoint((int)(x_coordinate + width), (int)y_coordinate);
        current_player_polygon.addPoint((int)(x_coordinate + width), (int)(y_coordinate + height));
        current_player_polygon.addPoint((int)x_coordinate, (int)(y_coordinate + height));

        original_player_polygon.addPoint((int)x_coordinate, (int)y_coordinate);
        original_player_polygon.addPoint((int)(x_coordinate + width), (int)y_coordinate);
        original_player_polygon.addPoint((int)(x_coordinate + width), (int)(y_coordinate + height));
        original_player_polygon.addPoint((int)x_coordinate, (int)(y_coordinate + height));

    }

    /**
     * Displays the player's polygon
     * @param g Graphics
     */
    public void display(Graphics g) {
        g.setColor(Color.black);
        g.fillPolygon(current_player_polygon);
    }

    public Vec2 getInitialPlayerDirection() {
        return initial_player_direction;
    }

    /**
     * Fetches the player's current direction vector
     * @return current direction vector
     */
    public Vec2 getCurrentPlayerDirection() {
        return current_player_direction;
    }

    /**
     * Fetch Player's width
     * @return player's width
     */
    public double getWidth() {
        return player_width;
    }

    /**
     * Fetch Player's height
     * @return player's height
     */
    public double getHeight() {
        return player_height;
    }

    /**
     * Retrieve the x-coordinate of the player's Center of Mass
     * @return x-coordinate of the centre of mass
     */
    public double getCentreX(){
        double result = 0.0;
        for (int x : current_player_polygon.xpoints){
            result += x;
        }
        return result / current_player_polygon.xpoints.length;
    }

    /**
     * Retrieve the y-coordinate of the player's Center of Mass
     * @return y-coordinate of the centre of mass
     */
    public double getCentreY() {
        double result = 0.0;
        for (int y : current_player_polygon.ypoints) {
            result += y;
        }
        return result/current_player_polygon.ypoints.length;
    }

    /**
     * Move the player up the screen
     */
    public void moveUp() {
        current_player_polygon.translate(0, (int) -speed);
    }

    /**
     * Move the player down the screen
     */
    public void moveDown() {
        current_player_polygon.translate(0, (int) speed);
    }

    /**
     * Move the player right along the screen
     */
    public void moveRight() {
        current_player_polygon.translate((int)speed, 0);
    }

    /**
     * Move the player left along the screen
     */
    public void moveLeft() {
        current_player_polygon.translate((int) -speed, 0);
    }

    /**
     * Rotates the play by the angle theta
     * @param theta angle in radians
     */
    public void rotate(double theta) {
        theta += (initial_player_direction.angleBetweenVec(current_player_direction));

        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double previous_x_coordinate = initial_player_direction.getX();
        double previous_y_coordinate = initial_player_direction.getY();

        current_player_direction.setX(previous_x_coordinate * cosTheta - previous_y_coordinate * sinTheta);
        current_player_direction.setY(previous_x_coordinate * sinTheta + previous_y_coordinate * cosTheta);

        int previous_centroid_x = (int) getCentreX();
        int previous_centroid_y = (int) getCentreY();

        //Translate player polygon to origin before rotating
        current_player_polygon.translate(-previous_centroid_x, -previous_centroid_y);
        for (int i = 0; i < current_player_polygon.xpoints.length ; i++) {
            previous_x_coordinate = original_player_polygon.xpoints[i];
            previous_y_coordinate = original_player_polygon.ypoints[i];
            current_player_polygon.xpoints[i] = (int) (previous_x_coordinate * cosTheta - previous_y_coordinate * sinTheta);
            current_player_polygon.ypoints[i] = (int) (previous_x_coordinate * sinTheta + previous_y_coordinate * cosTheta);
        }
        //Translate player polygon back to original point
        current_player_polygon.translate(previous_centroid_x, previous_centroid_y);
    }



}
