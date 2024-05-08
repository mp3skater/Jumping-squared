package net.mp3skater.main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {

        JFrame window = new JFrame("Jumping²");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Add GamePanel to the window:
        GamePanel gp = new GamePanel();
        GamePanel.titleState =true;
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();
    }
}