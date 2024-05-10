package net.mp3skater.main.obj;

import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_enemy extends Obj_moving {
    /*
    Enemy class for enemies with unique AI for each level
     */

    public Obj_enemy(double x, double y, int sX, int sY, int type) {
        super(x, y, sX, sY, 5,0,0,0);
        this.type = type;
        this.startPos = new int[]{(int)x, (int)y};
    }

    // The spawning position
    private final int[] startPos;

    // The type of enemy, used if there are multiple
    private final int type;

    @Override
    public void update() {
        // Add the AI/movement
        movement();
        // Updates the collision-checks
        super.move();
    }

    /*
    Movement-method with different outcomes for each level
     */
    private void movement() {
        switch(type) {
        /*
        Name: Block (no movement, size: 50*50)
         */
            case 0: return;

            case 1: return;
            case 2: {
                // The max. distance an enemy should(doesn't need to) go away from <startPos>
                int maxDist = 70;
                // If the enemy goes too much away from spawn, he goes to the other direction
                if(Math.abs(pos[0] - startPos[0]) > maxDist)
                    vec[0] *= -1;
            }
        }
    }

    @Override
    public boolean collides(double x, double y, int sX, int sY) {
        //if(type == 4) {
        //    Really strange math to calculate different collision
        //}
        return super.collides(x, y, sX, sY);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Draw_Utils.fillRect(g2, this, color);
    }
}