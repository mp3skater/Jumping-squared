package net.mp3skater.main.obj;

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
    protected boolean collides(Obj obj) {
        return collides((int)obj.pos[0], (int)obj.pos[1], obj.size[0], obj.size[1]);
    }
    protected boolean collides(int x, int y, int sX, int sY) {
        return x > pos[0] - sX && x < pos[0] + size[0] && y > pos[1] - sY && y < pos[1] + size[1];
    }

    /*
    Only draw an <Obj> if it is visible in the screen
     */
    public boolean is_drawable() {
        return collides((int)GamePanel.offset, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    /*

     */
    public int getDrawX(Obj obj) {
        return (int) (obj.pos[0] - GamePanel.offset);
    }

    /*
    Method that draws <this>
     */
    public abstract void draw(Graphics2D g2, Color color);

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