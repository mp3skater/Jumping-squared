package net.mp3skater.main.obj;

import net.mp3skater.main.Utils;

import java.awt.*;

public class Obj_arrow extends Obj {
    public Obj_arrow(double x, double y) {
        super(x, y, 0, 0);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Utils.drawArrow(g2, getDrawX(this), (int)pos[1], color);
    }
}
