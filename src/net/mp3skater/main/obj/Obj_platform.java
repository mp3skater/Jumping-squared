package net.mp3skater.main.obj;

import net.mp3skater.main.Utils;

import java.awt.*;

public class Obj_platform extends Obj_wall {
    /*
    Platform a player can place and jump on
    Enemies and other similar objects can also interact with it
     */

    public Obj_platform(double x, double y) {
        super(x, y, 130, 40);
    }

    public void setPos(int x, int y) {
        pos[0] = x;
        pos[1] = y;
    }

    /*
    Cloning method
     */
    public Obj_platform neu() {
        return new Obj_platform(pos[0], pos[1]);
    }

    /*
    Draws the aimPlatform with a border
     */
    public void drawAim(Graphics2D g2, Color color) {
        Utils.drawRect(g2, this, color);
    }
}
