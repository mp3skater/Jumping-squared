package net.mp3skater.main.elements;

import net.mp3skater.main.util.Utils;

import java.awt.*;

public class Obj_wall extends Obj {
    public Obj_wall(double x, double y, int sX, int sY) {
        super(x, y, sX, sY);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Utils.drawRect(g2, pos, size, color);
    }
}
