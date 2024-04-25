package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.level.Level;

import java.awt.*;

public class Board {

    /*
    Gets the color from the <currentLevel> and draws it on the entire screen
     */
    public void draw(Graphics2D g2, Level currentLevel) {
        g2.setColor(currentLevel.getColor("background"));
        g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}