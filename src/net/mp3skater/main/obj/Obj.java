package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public abstract class Obj {
    /*
    Superclass of all objects you can see on the screen
     */

    // Size and position of this
    protected final int[] size;
    protected final double[] pos;

    // If the collision is calculated and if it's going to be drawn this frame
    protected boolean isImportant;

    /*
    Simple constructor
     */
    public Obj(double x, double y, int sX, int sY, boolean isImportant) {
        this.isImportant = isImportant;
        this.size = new int[]{sX, sY};
        this.pos = new double[]{x, y};
    }

    /*
    Decides whether <this> get's drawn/calculated
     */
    public void setImportance(boolean importance) {
        this.isImportant = importance;
    }
    public boolean isImportant() {
        return isImportant;
    }

    /*
    Formula that checks if another <Obj> and this collide
     */
    public boolean collides(Obj obj) {
        return collides(obj.pos[0], obj.pos[1], obj.size[0], obj.size[1]);
    }
    public boolean collides(double x, double y, int sX, int sY) {
        return x > pos[0] - sX && x < pos[0] + size[0] && y > pos[1] - sY && y < pos[1] + size[1];
    }

    /*
    Checks collision with a point
     */
    public boolean collidesPoint(double x, double y) {
        return pos[0] <= x && pos[0] + size[0] >= x && pos[1] <= y && pos[1] + size[1] >= y;
    }

    /*
    Only draw an <Obj> if it is visible in the screen
     */
    public boolean is_drawable() {
        return collides((int)GamePanel.offset, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    /*
    Returns the right x-coordinate considering the offset
     */
    public int getDrawX() {
        return (int) (pos[0] - GamePanel.offset);
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
    // Adds a value to the position
    public void addX(double value) {
        pos[0] += value;
    }
    public void addY(double value) {
        pos[1] += value;
    }
}