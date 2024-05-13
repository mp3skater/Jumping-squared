package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.utils.Col_Utils;
import net.mp3skater.main.utils.Draw_Utils;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.utils.Sound_Utils;

import java.awt.*;

public class Obj_player extends Obj_moving {
    /*
    This is the player which you can control through the level.
    */


    public Obj_player(double x, double y) {
        super(x, y, 50, 80, 0, 0, 8, 15);
    }

    // If he touches the ground
    private boolean onGround;

    // To draw the last direction the player was going to
    private boolean left = false;

    private boolean gravity = true;

    /*
    Gives the player movement vectors according to the key's pressed
    */
    public void movement() {
        // Movement
        if(KeyHandler.aPressed) this.addVec(-0.5, 0);
        if(KeyHandler.dPressed) this.addVec(0.5, 0);
        // Jump
        if(KeyHandler.spacePressed && onGround) {
            if(GamePanel.level==3) {

            }
            this.addVec(0, -15);
            Sound_Utils.playSE(4);



        }
        // Gravity (positive value because the up-left corner is x:0,y:0)
        this.addVec(0, 0.6);
    }

    @Override
    protected void collisionBool() {
        // Reset onGround
        onGround = false;

        // Kill Player if he falls down
        if(pos[1] > GamePanel.HEIGHT) {
            GamePanel.gameOver();
            return;
        }

        // Dev tools:
        // Reset camera
        if(KeyHandler.zeroPressed) GamePanel.offset = pos[0] - GamePanel.WIDTH/2.0;
        // Change level
        if(KeyHandler.onePressed) {
            GamePanel.level = 1;
            GamePanel.loadLevel(1);
        }
        else if(KeyHandler.twoPressed) {
            GamePanel.level = 2;
            GamePanel.loadLevel(2);
        }
        else if(KeyHandler.threePressed) {
            GamePanel.level = 3;
            GamePanel.loadLevel(3);
        }
        else if(KeyHandler.fourPressed) {
            GamePanel.level = 4;
            GamePanel.loadLevel(4);
        }
        else if(KeyHandler.fivePressed)
            GamePanel.loadLevel(5);

        // Collision with left side of the screen
        if(pos[0]+vec[0]-(int)GamePanel.offset < 0) xCollision((int) (size[0]+GamePanel.offset), 0);

        // Update offset if the player moves forwards in the level
        if(pos[0]+size[0]-GamePanel.offset > GamePanel.WIDTH/2.0 && vec[0]>0) GamePanel.offset += vec[0];

        // Kill player if he hits an enemy
        for(Obj e : GamePanel.enemies)
            if(collides(e)) {
                GamePanel.gameOver();
                return;
            }

        // Load new level if player reaches the end-bar
        if(pos[0] >= GamePanel.getLength()-50) {
            if(GamePanel.level < 4) {
                GamePanel.level++;
                GamePanel.loadLevel(GamePanel.level);
            } else if(GamePanel.level == 4) {
                GamePanel.gameWon();
            }
            else throw new RuntimeException();
        }

        // Variables to check nearest collision
        Obj nearX = null, nearY = null;

        // For all elements you could collide with in walls
        for(Obj w : GamePanel.walls) {
            // Return if endbar to allow the player to finish the level
            if(w instanceof Obj_endBar)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(w.getX()-Col_Utils.absRound(vec[0]), w.getY(), (int)w.getSX(), (int)w.getSY())) {
                    nearX = nearX==null? w : getNearestX(nearX, w, vec[0] > 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(w.getX(), w.getY()-Col_Utils.absRound(vec[1]), (int)w.getSX(), (int)w.getSY())) {
                    if(vec[1]>0)
                        onGround = true;
                    nearY = nearY==null? w : getNearestY(nearY, w, vec[1] > 0);
                }
            // Both
            //if(vec[0] != 0 && vec[1] != 0)
            //    if(collides((int)(w.getX()-vec[0]), (int)(w.getY()-vec[1]), (int)w.getSX(), (int)w.getSY())) {
            //        System.out.println("colXY");
            //        // To avoid player from getting stuck
            //        if(!(!collides((int)(w.getX()-vec[0]), (int)w.getY(), (int)w.getSX(), (int)w.getSY()) &&
            //                !collides((int)w.getX(), w.getY()-vec[1], (int)w.getSX(), (int)w.getSY())))
            //            nearX = nearX==null? w : getNearestX(nearX, w, vec[0] > 0);
            //        nearY = nearY==null? w : getNearestY(nearY, w, vec[1] > 0);
            //    }
        }
        // For all elements you could collide with in platforms
        for(Obj p : GamePanel.platforms) {
            if(p == null)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(p.getX()-Col_Utils.absRound(vec[0]), p.getY(), (int)p.getSX(), (int)p.getSY())) {
                    nearX = nearX==null? p : getNearestX(nearX, p, vec[0] > 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(p.getX(), p.getY()-Col_Utils.absRound(vec[1]), (int)p.getSX(), (int)p.getSY())) {
                    if(vec[1]>0)
                        onGround = true;
                    nearY = nearY==null? p : getNearestY(nearY, p, vec[1] > 0);
                }
            // Both
            //if(vec[0] != 0 && vec[1] != 0)
            //    if(collides((int)(p.getX()-vec[0]), (int)(p.getY()-vec[1]), (int)p.getSX(), (int)p.getSY())) {
            //        // To avoid player from getting stuck
            //        if(!(!collides((int)(p.getX()-vec[0]), (int)p.getY(), (int)p.getSX(), (int)p.getSY()) &&
            //                !collides((int)p.getX(), p.getY()-vec[1], (int)p.getSX(), (int)p.getSY())))
            //            nearX = nearX==null? p : getNearestX(nearX, p, vec[0] > 0);
            //        nearY = nearY==null? p : getNearestY(nearY, p, vec[1] > 0);
            //    }
        }
        // Actual collision:

        // To avoid players from getting stuck
        if (nearX != null)
            if (nearY != null && nearX == nearY && (int) nearX.getX() == nearX.getX() && (int) nearY.getY() == nearY.getY()) {
                yCollision((int) nearY.getY(), vec[1] < 0/*going up*/ ? nearY.size[1] : 0);
                return;
            }

        if(nearX!=null) {
            xCollision((int)nearX.getX(), vec[0]<0/*going left*/? nearX.size[0] : 0);
        }
        if(nearY!=null) {
            yCollision((int)nearY.getY(), vec[1]<0/*going up*/? nearY.size[1] : 0);
        }
    }

    /*
    Returns the nearest Obj to collide with
    <positive> means the player is going right or down
     */
    private Obj getNearestX(Obj o1, Obj o2, boolean positive) {
        if(positive) return o1.getX()<o2.getY()? o1 : o2;
        else return o1.getX()+o1.getSX()>o2.getX()+o2.getSX()? o1 : o2;
    }
    private Obj getNearestY(Obj o1, Obj o2, boolean positive) {
        if(positive) return o1.getY()<o2.getY()? o1 : o2;
        else return o1.getY()+o1.getSY()>o2.getY()+o2.getSY()? o1 : o2;
    }

    /*
    Overrides the update class to include the <movement()> and the <turndownvec()> methods
    */
    @Override
    public void update() {
        // Adds the movement vectors
        movement();
        // Updates the collision-checks and changes the player
        super.update();
        // Make the horizontal vector's slower so the player doesn't glide forever
        turndownvec();
    }

    /*
    Makes the <Player> slow down horizontally to ensure it doesn't drift away
     */
    public void turndownvec() {
        // No movement
        if(vec[0] == 0)
            return;

        // Value with which the <Obj> loses speed horizontally
        // Kind of like how "unslippery" the ground is for the <Obj>
        double turnDown = onGround? /*On the ground*/0.3 : /*In the air*/0.25;
        if(vec[0]>0) {
            if(vec[0]- turnDown < 0) vec[0] = 0;
            else vec[0] -= turnDown;
        }
        else {
            if(vec[0]+ turnDown > 0) vec[0] = 0;
            else vec[0] += turnDown;
        }
    }

    /*
    Returns the vector of the player, for the log
     */
    public int getVX() {
        return (int)vec[0];
    }
    public int getVY() {
        return (int)vec[1];
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        if(GamePanel.won)
            drawAmogus(g2, vec[0]==0? left : vec[0] < 0);
        else if(GamePanel.level==3) {
            g2.setColor(Color.black);
            g2.drawRect(getDrawX(), (int) pos[1], size[0], size[1]);
        }
        else
            Draw_Utils.fillRect(g2, this, color);
    }

    /*
    Easteregg if you finish the game
     */
    private void drawAmogus(Graphics2D g2, boolean left) {
        if(left) {
            g2.setColor(Color.red);
            g2.fillRect(getDrawX(), (int)pos[1], 50, 65);
            g2.fillRect(getDrawX(), (int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+32,(int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+size[0], (int)pos[1]+15, 15, 35);
            g2.setColor(Color.cyan);
            g2.fillRect(getDrawX()-10, (int)pos[1]+10, 50, 30);
            g2.setColor(Color.white);
            g2.fillRect(getDrawX(), (int)pos[1]+15, 20, 5);
            this.left = true;
        }
        else {
            g2.setColor(Color.red);
            g2.fillRect(getDrawX(), (int)pos[1], 50, 65);
            g2.fillRect(getDrawX(), (int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+32,(int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()-15, (int)pos[1]+15, 15, 35);
            g2.setColor(Color.cyan);
            g2.fillRect(getDrawX()+10, (int)pos[1]+10, 50, 30);
            g2.setColor(Color.white);
            g2.fillRect(getDrawX()+33, (int)pos[1]+15, 20, 5);
            this.left = false;
        }
    }
}