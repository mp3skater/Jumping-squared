package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.util.Utils;

import java.awt.*;

public class Obj_enemy extends Obj_moving {
    public Obj_enemy(double x, double y) {
        super(x, y, 50, 50, 5,0,0,0);
        this.startPos = new int[]{(int)x, (int)y};
    }

    // The spawning position
    private final int[] startPos;

    @Override
    public void update() {
        // Add the AI/movement
        movement();
        // Updates the collision-checks
        super.move();
    }

    private void movement() {
        switch(GamePanel.level) {
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
    public void draw(Graphics2D g2, Color color) {
        Utils.drawRect(g2, this, color);
    }
}