package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_enemy extends Obj_moving {
    /*
    Enemy class for enemies with unique AI for each level
     */

    public Obj_enemy(double x, double y, int sX, int sY, int type, int maxDistance, int maxSpeed) {
        super(x, y, sX, sY, 0,0,0,0);
        this.type = type;
        this.maxDistance = maxDistance;
        this.maxSpeed = maxSpeed;
        this.startPos = new int[]{(int)x, (int)y};
    }

    //#
    private final int maxSpeed;

    // The maximal distance the enemy will go away from spawn (doesn't need to be implemented)
    private final int maxDistance;

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
        Spike: Nr.4 (no movement, triangle)
        Moving Catch Platform: Nr.5 (Size: 800*600, moves only forward)
         */
            case 0: return;

            case 1://Level 4; up/down movement / hallo domenik
                //maxDistance = 160;
                if(vec[1]==0)
                    vec[1]=maxSpeed;
                // If the enemy goes too much away from spawn, he goes to the other direction
                if(Math.abs(pos[1] - startPos[1]) > maxDistance)
                    vec[1] *= -1;
                break;
            case 2: {//Level 4; left/right movement
                //maxDistance = 350;
                if(vec[0]==0)
                    vec[0]=maxSpeed;
                if(Math.abs(pos[0] - startPos[0]) > maxDistance)
                    vec[0] *= -1;
                break;
            }
//            case 3: {//Level 4; up/down movement
//                //maxDistance = 160;
//                if(vec[1]==0)
//                    vec[1]=-maxSpeed;
//                if(Math.abs(pos[1] - startPos[1]) > maxDistance)
//                    vec[1] *= -1;
//                break;
//            }
            case 4: break;
            case 5: // Level 3; infinite movement forwards
                if(vec[0]==0)
                    vec[0]=maxSpeed;
        }
    }

    @Override
    public boolean collides(double x, double y, int sX, int sY) {
        if(type == 4) {
            Obj_player p = GamePanel.getPlayer();
            return p.collidesPoint(pos[0],pos[1] + size[1]) ||
                   p.collidesPoint(pos[0] + (int)(size[0]/2),pos[1]) ||
                   p.collidesPoint(pos[0] + size[0], pos[1] + size[1]);
        }
        return super.collides(x, y, sX, sY);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        if(type == 4) { // Spike
            Draw_Utils.drawSpike(g2, getDrawX(), (int)pos[1], size[0], size[1], color);
            Draw_Utils.fillRect(g2, this, color);
        }
        else Draw_Utils.fillRect(g2, this, color);
    }
}