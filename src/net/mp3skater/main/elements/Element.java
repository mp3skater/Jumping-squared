package net.mp3skater.main.elements;

import java.awt.*;

public class Element {

    /*
    This is an Object-example, you may change it to your liking.
     */

    // Variables for the position, the size and the color of the element
    private double[] pos;
    private final int size;
    private final Color color;

    // Constructor
    public Element(double x, double y, int size, Color color) {
        this.color = color;
        pos = new double[]{x, y};
        this.size = size;
    }

    // Draw method which is called every frame
    public void draw(Graphics2D g2) {

        // Filled space in the circle
        g2.setColor(color);
        g2.fillOval((int) this.getX(),(int) this.getY(), size, size);

        // Black border around the circle
        g2.setColor(Color.black);
        g2.drawOval((int) this.getX(),(int) this.getY(), size, size);
    }

    // Position getter
    public double getX() {
        return pos[0];
    }
    public double getY() {
        return pos[1];
    }

    // Position setter
    public void setX(double x) {
        pos[0] = x;
    }
    public void setY(double y) {
        pos[1] = y;
    }
}
