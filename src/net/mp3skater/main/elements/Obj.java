package net.mp3skater.main.elements;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public abstract class Obj {
    // Size and position of the Obj
    protected final int[] size;
    protected final double[] pos;

    public Obj(double x, double y, int sX, int sY) {
        this.size = new int[]{sX, sY};
        this.pos = new double[]{x, y};
    }

    /*
    Formula that checks if another <Obj> and this collide
     */
    protected boolean collides(int x, int y, int sX, int sY) {
        return x > pos[0] - sX && x < pos[0] + size[0] && y > pos[1] - sY && y < pos[1] + size[1];
    }

    public boolean is_drawable() {
        return collides((int)GamePanel.offset, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    /*
    Only draw an <Obj> if it's inside the screen
     */
    public abstract void draw(Graphics2D g2);

    // Position getter
    public double getX() {
        return pos[0];
    }
    public double getY() {
        return pos[1];
    }

    // Size getter
    public double getSX() {
        return size[0];
    }
    public double getSY() {
        return size[1];
    }
}