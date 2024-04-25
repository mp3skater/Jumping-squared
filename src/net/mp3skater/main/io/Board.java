package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Board {

    /*
    Draws the background depending on the level
     */

    public void draw(Graphics2D g2) {
        //g2.setColor(Level.getColor(5));
        g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}