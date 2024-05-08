package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    /*
    This class handles the key presses and stores them in global variables
     */

    public static boolean pausePressed, aPressed, dPressed, spacePressed, zeroPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(GamePanel.titleState){
            if(keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) {
                GamePanel.comandNum--;
                if (GamePanel.comandNum < 0) {
                    GamePanel.comandNum = 2;
                }
            }
            if(keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
                GamePanel.comandNum++;
                if (GamePanel.comandNum > 2) {
                    GamePanel.comandNum = 0;
                }
            }
            if(keyCode ==KeyEvent.VK_ENTER){
                if(GamePanel.comandNum==0){
                    GamePanel.titleState=false;
                }
                if(GamePanel.comandNum==1){
                    //Open Records File
                }
                if(GamePanel.comandNum==2)
                    System.exit(0);
            }
        }

        // Dev tool
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = true;
        // For the start of the game
        if(keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER)
            pausePressed = true;
        if(keyCode == KeyEvent.VK_A)
            aPressed = true;
        // Allow also H instead of d because of workman compatibility
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H)
            dPressed = true;
        if(keyCode == KeyEvent.VK_SPACE)
            spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Dev tool
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = false;
        if(keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER)
            pausePressed = false;
        if(keyCode == KeyEvent.VK_A)
            aPressed = false;
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H)
            dPressed = false;
        if(keyCode == KeyEvent.VK_SPACE)
            spacePressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}