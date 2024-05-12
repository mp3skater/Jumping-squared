package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
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

    /*
    Gives the player movement vectors according to the key's pressed
    */
    //movement
    public void movement() {
        if(KeyHandler.aPressed)
            this.addVec(-0.5, 0);
        if(KeyHandler.dPressed)
            this.addVec(0.5, 0);
        //jump
        if(KeyHandler.spacePressed && onGround) {
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
        if(KeyHandler.zeroPressed)
            GamePanel.offset = pos[0] - GamePanel.WIDTH/2.0;
        // Change level
        if(KeyHandler.onePressed)
            GamePanel.loadLevel(1);
        else if(KeyHandler.twoPressed)
            GamePanel.loadLevel(2);
        else if(KeyHandler.threePressed)
            GamePanel.loadLevel(3);
        else if(KeyHandler.fourPressed)
            GamePanel.loadLevel(4);

        // Collision with left side of the screen
        if(pos[0]+vec[0]-(int)GamePanel.offset < 0)
            xCollision((int) (size[0]+GamePanel.offset), 0);

        // Update offset if the player moves forwards in the level
        if(pos[0]+size[0]-GamePanel.offset > GamePanel.WIDTH/2.0 && vec[0]>0)
            GamePanel.offset += vec[0];//pos[0]+size[0]-GamePanel.WIDTH/2.0;

        // Kill player if he hits an enemy
        for(Obj e : GamePanel.enemies)
            if(collides(e)) {
                GamePanel.gameOver();
                return;
            }

        // Load new level if player reaches the end-bar
        if(pos[0] >= GamePanel.getLength()-50) {
            GamePanel.level++;
            GamePanel.loadLevel(GamePanel.level);
        }

        // For all elements you could collide with in walls
        for(Obj w : GamePanel.walls) {
            // Return if endbar to allow the player to finish the level
            if(w instanceof Obj_endBar)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(w.getX()-vec[0], w.getY(), (int)w.getSX(), (int)w.getSY())) {
                    xCollision((int)w.getX(), vec[0]<0/*going left*/? w.size[0] : 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(w.getX(), w.getY()-vec[1], (int)w.getSX(), (int)w.getSY())) {
                    if(vec[1]>0)
                        onGround = true;
                    yCollision((int)w.getY(), vec[1]<0/*going up*/? w.size[1] : 0);
                }
            // Both
            if(vec[0] != 0 && vec[1] != 0)
                if(collides(w.getX()-vec[0], w.getY()-vec[1], (int)w.getSX(), (int)w.getSY())) {
                    // To avoid player from getting stuck
                    if(!(!collides(w.getX()-vec[0], w.getY(), (int)w.getSX(), (int)w.getSY()) &&
                            !collides(w.getX(), w.getY()-vec[1], (int)w.getSX(), (int)w.getSY())))
                        xCollision((int)w.getX(), vec[0]<0/*going left*/? w.size[0] : 0);
                    yCollision((int)w.getY(), vec[1]<0/*going up*/? w.size[1] : 0);
                }
        }
        // For all elements you could collide with in platforms
        for(Obj p : GamePanel.platforms) {
            if(p == null)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(p.getX()-vec[0], p.getY(), (int)p.getSX(), (int)p.getSY())) {
                    xCollision((int)p.getX(), vec[0]<0/*going left*/? p.size[0] : 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(p.getX(), p.getY()-vec[1], (int)p.getSX(), (int)p.getSY())) {
                    if(vec[1]>0)
                        onGround = true;
                    yCollision((int)p.getY(), vec[1]<0/*going up*/? p.size[1] : 0);
                }
            // Both
            if(vec[0] != 0 && vec[1] != 0)
                if(collides(p.getX()-vec[0], p.getY()-vec[1], (int)p.getSX(), (int)p.getSY())) {
                    // To avoid player from getting stuck
                    if(!(!collides(p.getX()-vec[0], p.getY(), (int)p.getSX(), (int)p.getSY()) &&
                            !collides(p.getX(), p.getY()-vec[1], (int)p.getSX(), (int)p.getSY())))
                        xCollision((int)p.getX(), vec[0]<0/*going left*/? p.size[0] : 0);
                    yCollision((int)p.getY(), vec[1]<0/*going up*/? p.size[1] : 0);
                }
        }
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

        // In the air // EXPERIMENTAL: Not good for control, feeling: Strange
        //if(!onGround)
        //    return;

        // Value with which the <Obj> loses speed horizontally
        // Kind of like how "unslippery" the ground is for the <Obj>
        double turnDown = onGround? /*On the ground*/0.3 : /*In the air*/0.25;
        if(vec[0]>0) {
            if(vec[0]- turnDown < 0)
                vec[0] = 0;
            else
                vec[0] -= turnDown;
        }
        else {
            if(vec[0]+ turnDown > 0)
                vec[0] = 0;
            else
                vec[0] += turnDown;
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