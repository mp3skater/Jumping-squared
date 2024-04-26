package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.util.Utils;
import net.mp3skater.main.io.KeyHandler;

import java.awt.*;

/*
This is the player which you can control through the level.
*/

public class Obj_player extends Obj_moving {
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
            this.addVec(-1, 0);
        if(KeyHandler.dPressed)
            this.addVec(1, 0);
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
        if(pos[0]+vec[0] < 0)
            xCollision(size[0], 0);

        // For all elements you could collide with
        for(Obj o : GamePanel.objs) {
            if (collides((int) (o.getX() - vec[0]), (int) o.getY(), (int) o.getSX(), (int) o.getSY()) ||
                    collides((int) o.getX(), (int) (o.getY() - vec[1]), (int) o.getSX(), (int) o.getSY()) ||
                    collides((int) (o.getX() - vec[0]), (int) (o.getY() - vec[1]), (int) o.getSX(), (int) o.getSY())) {
                // Level finished
                if(o instanceof Obj_endBar) {
                    GamePanel.loadNewLevel();
                    break;
                }
                // Die when hitting an enemy
                if(o instanceof Obj_enemy) {
                    GamePanel.gameOver();
                    break;
                }
            }
            // No collision
            else continue;

            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(collides((int)(o.getX()-vec[0]), (int)o.getY(), (int)o.getSX(), (int)o.getSY())) {
                xCollision((int)o.getX(), vec[0]<0/*going up*/? o.size[0] : 0);
            }
            // Vertically
            if(collides((int)o.getX(), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                yCollision((int)o.getY(), vec[1]<0/*going left*/? o.size[1] : 0);
                onGround = true;
            }
            // Both
            if(collides((int)(o.getX()-vec[0]), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                xCollision((int)o.getX(), vec[0]<0/*going up*/? o.size[0] : 0);
                yCollision((int)o.getY(), vec[1]<0/*going left*/? o.size[1] : 0);
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
        double turnDown = onGround? /*On the ground*/0.5 : /*In the air*/0.2;
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
        Utils.drawRect(g2, pos, size, color);
    }
}