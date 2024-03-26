package net.mp3skater.utils;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Board {
    // VARIABLES


    public void draw(Graphics2D g2) {

        g2.setColor(Color.gray);
        g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}