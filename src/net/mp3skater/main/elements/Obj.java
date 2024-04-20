package net.mp3skater.main.elements;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Obj {
    // Size and position of the Obj
    protected final int[] size;
    protected final double[] pos;

    // Other variables
    private final Color color;

    public Obj(double x, double y, int sX, int sY, Color color) {
        this.size = new int[]{sX, sY};
        this.pos = new double[]{x, y};
        this.color = color;
    }

    /*
    Formula that checks if another <Obj> and this collide
     */
    protected boolean collides(int x, int y, int sX, int sY) {
        return x > pos[0] - sX && x < pos[0] + size[0] && y > pos[1] - sY && y < pos[1] + size[1];
    }

    /*
    Only draw an <Obj> if it's inside the screen
     */
    public void draw(Graphics2D g2) {
        if(collides((int)GamePanel.offset, 0, GamePanel.WIDTH, GamePanel.HEIGHT)) {
            g2.setColor(color);
            g2.fillRect((int)(pos[0]+GamePanel.offset), (int)(pos[1]+GamePanel.offset), size[0], size[1]);
        }
    }

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