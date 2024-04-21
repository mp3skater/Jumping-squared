package net.mp3skater.main.elements;

import net.mp3skater.main.Utils;
import net.mp3skater.main.io.KeyHandler;

import java.awt.*;

/*
This is the player which you can control through the level.
*/

public class Player extends Obj_moving {
    public Player(double x, double y, int sX, int sY, double vX, double vY, Color color, double turnDown) {
        super(x, y, sX, sY, vX, vY);
        this.turnDown = turnDown;
        this.color = color;
    }

    // Value with which the <Obj> loses speed horizontally
    // Kind of like how "unslippery" the surface is for the <Obj>
    private final double turnDown;

    // Color of the player
    private final Color color;

    /*
    Gives the player movement vectors according to the key's pressed
    */
    public void movement() {
        if(KeyHandler.aPressed)
            this.addVec(-1, 0);
        if(KeyHandler.dPressed)
            this.addVec(1, 0);
        if(KeyHandler.spacePressed)
            this.addVec(0, -15);

        // Gravity
        this.addVec(0, 0.4);
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
        if(vec[0] == 0)
            return;

        if(vec[0]>0) {
            if(vec[0]-turnDown < 0)
                vec[0] = 0;
            else
                vec[0] -= turnDown;
        }
        else {
            if(vec[0]+turnDown > 0)
                vec[0] = 0;
            else
                vec[0] += turnDown;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Utils.drawRect(g2, pos, size, color);
    }
}