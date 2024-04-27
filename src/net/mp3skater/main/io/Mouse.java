package net.mp3skater.main.io;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    /*
    Stores the location of the mouse and weather or not it is pressed in global variables
     */

    public int x, y;
    public boolean pressed;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}