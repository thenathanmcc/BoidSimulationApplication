/**
 * BoidSimulationApp.java
 *
 * @author Nathan McCulloch
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.Random;

public class BoidSimulationApp{
    private static JFrame gameFrame;

    public static void main(String args[]) {
        gameFrame = new JFrame("Boids, Boids, Boids!!");
        DisplayPanel display = new DisplayPanel();
        gameFrame.add(display);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setLocation(300, 50);

        // Game Loop
        while (true) {
            display.updatePlayerRotation();
            display.updateNPO();
            display.flock.run();
            
            // Force game loop to wait 10 ms
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // Redraw display
            display.repaint();
        }
    }


    private static class DisplayPanel extends JPanel {
        private int panel_width = 750;
        private int panel_height = 750;
        private Player player;

        private Vec2 currentMousePoint;
        private Vec2 currentPlayerPoint;
        private Vec2 newPlayerDirection;
        private double theta = 0.0;

        public Flock flock = new Flock();

        private int score;
        private Random generator = new Random();

        //Timers used for constant keypress
        Timer timerUp;
        Timer timerDown;
        Timer timerRight;
        Timer timerLeft;
        private final int DELAY = 20;

        public DisplayPanel () {
            setFocusable(true);
            setPreferredSize( new Dimension(panel_width, panel_height));
            setBackground(Color.white);

            player = new Player(250.0, 25.0, 20.0, 20.0);

            GameKeyListener kListener = new GameKeyListener();
            addKeyListener(kListener);
            timerUp = new Timer(DELAY, new GameKeyBoardControl("UP"));
            timerDown = new Timer(DELAY, new GameKeyBoardControl("DOWN"));
            timerRight = new Timer(DELAY, new GameKeyBoardControl("RIGHT"));
            timerLeft = new Timer(DELAY, new GameKeyBoardControl("LEFT"));

            currentMousePoint = new Vec2();
            currentPlayerPoint = new Vec2();

            GameMouseEventListener gameMouseEventListener = new GameMouseEventListener();
            addMouseListener(gameMouseEventListener);
        }


        /**
         * Update the players rotation using the current player position and the current mouse position
         */
        public void updatePlayerRotation(){
            currentMousePoint.setX(MouseInfo.getPointerInfo().getLocation().getX());
            currentMousePoint.setY(MouseInfo.getPointerInfo().getLocation().getY());
            currentPlayerPoint.setX((player.getCentreX() + gameFrame.getLocation().getX()));
            currentPlayerPoint.setY((player.getCentreY() + gameFrame.getLocation().getY()));
            newPlayerDirection = currentPlayerPoint.subVec(currentMousePoint);
            //System.out.println("New Player Direction: " + newPlayerDirection);
            theta = player.getCurrentPlayerDirection().angleBetweenVec(newPlayerDirection);
            newPlayerDirection.normalize();
            player.rotate(theta);
        }


        /**
         * Update the non-player objects in the game
         */
        public void updateNPO() {
            ArrayList<Projectile> projectiles = player.getProjectiles();
            ArrayList<Boid> boids = flock.getBoids();
            //System.out.println(projectiles.size());

            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).move();
                for (int j = 0; j < boids.size(); j++) {
                    Projectile projectile = projectiles.get(i);
                    Boid boid = boids.get(j);
                    Polygon boidPoly = new Polygon();
                    boidPoly.addPoint((int)boid.location.getX(), (int)boid.location.getY());
                    boidPoly.addPoint((int)(boid.location.getX() + boid.radius), (int)boid.location.getY());
                    boidPoly.addPoint((int)(boid.location.getX() + boid.radius), (int)(boid.location.getY() + boid.radius));
                    boidPoly.addPoint((int)(boid.location.getX()), (int)(boid.location.getY() + boid.radius));

                    // Check boid-projectile collisions
                    if (boidPoly.intersects(projectile.getCentreX(), projectile.getCentreX(), projectile.getWidth(), projectile.getLength())) {
                        score += 1;
                        projectile.setDead();
                        boid.setDead();
                        System.out.println("Score: " + score);
                    }

                    // Check boid-player collisions
                    if (boidPoly.intersects(player.getCentreX() - player.getWidth()/2.0, player.getCentreY() - player.getHeight()/2.0, player.getWidth(), player.getHeight())){
                        System.out.println("Player Hit!");
                        boid.setDead();
                        if (score > 0) {
                            score -= 1;
                        }
                        System.out.println("Score: " + score);
                    }
                }
            }

            Iterator projectilesIter = projectiles.iterator();
            Iterator boidsIter = boids.iterator();
            while (projectilesIter.hasNext()) {
                Projectile projectile = (Projectile) projectilesIter.next();
                if (projectile.isDead()) {
                    projectilesIter.remove();
                }
            }

            while (boidsIter.hasNext()) {
                Boid boid = (Boid) boidsIter.next();
                if (boid.dead) {
                    boidsIter.remove();
                }
            }
        }

        /** Class to start timers for constant key presses */
        private class GameKeyListener implements KeyListener {
            public void keyTyped (KeyEvent e) {

            }

            public synchronized void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    timerUp.start();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    timerDown.start();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    timerRight.start();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    timerLeft.start();
                } else if (e.getKeyCode() == KeyEvent.VK_B) {
                    flock.addBoid(new Boid(generator.nextInt(500), generator.nextInt(500), player));
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    timerUp.stop();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    timerDown.stop();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    timerRight.stop();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    timerLeft.stop();
                }
            }
        }

        /** Class to listen for Keyboard Events */
        private class GameKeyBoardControl extends AbstractAction {
            private String key;

            public GameKeyBoardControl(){}

            public GameKeyBoardControl(String key) {
                this.key = key;
            }

            public void actionPerformed(ActionEvent e) {
                if (key.equals("UP")) {
                    player.moveUp();
                } else if (key.equals("DOWN")) {
                    player.moveDown();
                } else if (key.equals("RIGHT")) {
                    player.moveRight();
                } else if (key.equals("LEFT")) {
                    player.moveLeft();
                }
            }
        }

        /** Class to listen to Mouse Events */
        private class GameMouseEventListener extends MouseAdapter {
            public void mouseDragged(MouseEvent e) {}

            public void mouseMoved(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                player.fireGun(newPlayerDirection);
            }
        }

        /**
         * Render the next frame
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            player.display(g);
            ArrayList<Projectile> projectiles = player.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).display(g);
            }
            flock.display(g);
        }
    }
}
