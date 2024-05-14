package net.mp3skater.main.obj;

import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_enemy extends Obj_moving {
    /*
    Enemy class for enemies with unique AI for each level
     */

    public Obj_enemy(double x, double y, int sX, int sY, int type) {
        super(x, y, sX, sY, 0,0,0,0);
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

            case 1://Level 4; up/down movement
                int maxDist1 = 160;
                if(vec[1]==0)
                    vec[1]=7;
                // If the enemy goes too much away from spawn, he goes to the other direction
                if(Math.abs(pos[1] - startPos[1]) > maxDist1)
                    vec[1] *= -1;
                break;
            case 2: {//Level 4; left/right movement
                int maxDist2 = 350;
                if(vec[0]==0)
                    vec[0]=10;
                if(Math.abs(pos[0] - startPos[0]) > maxDist2)
                    vec[0] *= -1;
                break;
            }
            case 3: {//Level 4; up/down movement
                int maxDist3 = 160;
                if(vec[1]==0)
                    vec[1]=-11;
                if(Math.abs(pos[1] - startPos[1]) > maxDist3)
                    vec[1] *= -1;
                break;
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