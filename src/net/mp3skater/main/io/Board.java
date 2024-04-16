package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Board {

    /*
    Here you can draw the background of your game.
     */

    // Variables
    private static final int SQUARE_SIZE = 100;
    private static Color color = Color.white;

    // For this example it will draw a sort of chess-board
    public void draw(Graphics2D g2) {
        // For all positions that are still inside the board
        for(int y = 0; SQUARE_SIZE*y < GamePanel.HEIGHT; y++) {
            for(int x = 0; SQUARE_SIZE*x < GamePanel.WIDTH; x++) {

                // Change color
                color = color == Color.white ? Color.black : Color.white;

                // Draw the square with the changed color
                g2.setColor(color);
                g2.fillRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
            // Change the color again at the end of each row
            color = color == Color.white ? Color.black : Color.white;
        }
    }
}