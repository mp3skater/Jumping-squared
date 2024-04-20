package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Board {

    /*
    Draw the background depending on the level
     */

    public void draw(Graphics2D g2) {
        switch(GamePanel.level) {
            case 1: g2.setColor(Color.black); break;
            case 2: g2.setColor(Color.getColor("dark-blue", 0x000034)); break;
            case 3: g2.setColor(Color.getColor("dark-yellow", 0x003400)); break;
            case 4: g2.setColor(Color.getColor("dark-purple", 0x240048)); break;
            case 5: g2.setColor(Color.getColor("dark-red", 0x340000)); break;
        }
        g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}