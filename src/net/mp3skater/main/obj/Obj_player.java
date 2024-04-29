package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.Utils;
import net.mp3skater.main.io.KeyHandler;

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

    /*
    Gives the player movement vectors according to the key's pressed
    */
    public void movement() {
        if(KeyHandler.aPressed)
            this.addVec(-0.5, 0);
        if(KeyHandler.dPressed)
            this.addVec(0.5, 0);
        if(KeyHandler.spacePressed && onGround)
            this.addVec(0, -15);

        // Gravity (positive value because the up-left corner is x:0,y:0)
        this.addVec(0, 0.6);
    }

    @Override
    protected void collisionBool() {
        // Reset onGround
        onGround = false;

        // Kill Player if he falls down
        if(pos[1] > GamePanel.HEIGHT)
            GamePanel.gameOver();

        // Collision with left side of the screen
        if(pos[0]+vec[0]-GamePanel.offset < 0)
            xCollision((int) (size[0]+GamePanel.offset), 0);

        // Update Offset if the player moves forwards in the level
        if(pos[0]+size[0]-GamePanel.offset > GamePanel.WIDTH/2.0 && vec[0]>0)
            GamePanel.offset += vec[0];//pos[0]+size[0]-GamePanel.WIDTH/2.0;

        // Kill player if he hits an enemy
        for(Obj e : GamePanel.enemies)
            if(collides(e)) {
                GamePanel.gameOver();
                return;
            }

        // Load new level if player reaches the end-bar
        if(pos[0] >= GamePanel.getLength()-50)
            GamePanel.loadNextLevel();

        // For all elements you could collide with in walls
        for(Obj w : GamePanel.walls) {
            // Return if endbar to allow the player to finish the level
            if(w instanceof Obj_endBar)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(collides((int)(w.getX()-vec[0]), (int)w.getY(), (int)w.getSX(), (int)w.getSY())) {
                xCollision((int)w.getX(), vec[0]<0/*going up*/? w.size[0] : 0);
            }
            // Vertically
            if(collides((int)w.getX(), (int)(w.getY()-vec[1]), (int)w.getSX(), (int)w.getSY())) {
                yCollision((int)w.getY(), vec[1]<0/*going left*/? w.size[1] : 0);
                onGround = true;
            }
            // Both
            if(collides((int)(w.getX()-vec[0]), (int)(w.getY()-vec[1]), (int)w.getSX(), (int)w.getSY())) {
                xCollision((int)w.getX(), vec[0]<0/*going up*/? w.size[0] : 0);
                yCollision((int)w.getY(), vec[1]<0/*going left*/? w.size[1] : 0);
            }
        }
        // For all elements you could collide with in platforms
        for(Obj p : GamePanel.platforms) {
            if(p == null)
                continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(collides((int)(p.getX()-vec[0]), (int)p.getY(), (int)p.getSX(), (int)p.getSY())) {
                xCollision((int)p.getX(), vec[0]<0/*going up*/? p.size[0] : 0);
            }
            // Vertically
            if(collides((int)p.getX(), (int)(p.getY()-vec[1]), (int)p.getSX(), (int)p.getSY())) {
                yCollision((int)p.getY(), vec[1]<0/*going left*/? p.size[1] : 0);
                onGround = true;
            }
            // Both
            if(collides((int)(p.getX()-vec[0]), (int)(p.getY()-vec[1]), (int)p.getSX(), (int)p.getSY())) {
                xCollision((int)p.getX(), vec[0]<0/*going up*/? p.size[0] : 0);
                yCollision((int)p.getY(), vec[1]<0/*going left*/? p.size[1] : 0);
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

    @Override
    public void draw(Graphics2D g2, Color color) {
        Utils.fillRect(g2, this, color);
    }
}