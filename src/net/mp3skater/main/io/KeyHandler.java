package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static net.mp3skater.main.GamePanel.loadLevel;
import static net.mp3skater.main.utils.Sound_Utils.playSE;
import static net.mp3skater.main.utils.Sound_Utils.stopMusic;

public class KeyHandler implements KeyListener {
    /*
    This class handles the key presses and stores them in global variables
     */

    public static boolean escPressed, aPressed, dPressed, wPressed, sPressed, spacePressed, zeroPressed, onePressed,
            twoPressed, threePressed, fourPressed,fivePressed, upPressed, downPressed, leftPressed, rightPressed,
            backPressed,backControlPressed, enterPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Dev tools
        if(keyCode == KeyEvent.VK_0) zeroPressed = true;
        if(keyCode == KeyEvent.VK_1) onePressed = true;
        if(keyCode == KeyEvent.VK_2) twoPressed = true;
        if(keyCode == KeyEvent.VK_3) threePressed = true;
        if(keyCode == KeyEvent.VK_4) fourPressed = true;
        if(keyCode == KeyEvent.VK_5) fivePressed = true;
        if(keyCode == KeyEvent.VK_UP) upPressed = true;
        if(keyCode == KeyEvent.VK_DOWN) downPressed = true;
        if(keyCode == KeyEvent.VK_LEFT) leftPressed = true;
        if(keyCode == KeyEvent.VK_RIGHT) rightPressed = true;

        // For the start of the game
        if(keyCode == KeyEvent.VK_ESCAPE) escPressed = true;
        if(keyCode == KeyEvent.VK_ENTER) enterPressed = true;
        if(keyCode == KeyEvent.VK_A) aPressed = true;
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H) dPressed = true;
        if(keyCode == KeyEvent.VK_SPACE) spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Dev tools
        if(keyCode == KeyEvent.VK_0) zeroPressed = false;
        if(keyCode == KeyEvent.VK_1) onePressed = false;
        if(keyCode == KeyEvent.VK_2) twoPressed = false;
        if(keyCode == KeyEvent.VK_3) threePressed = false;
        if(keyCode == KeyEvent.VK_4) fourPressed = false;
        if(keyCode == KeyEvent.VK_5) fivePressed = false;
        if(keyCode == KeyEvent.VK_UP) upPressed = false;
        if(keyCode == KeyEvent.VK_DOWN) downPressed = false;
        if(keyCode == KeyEvent.VK_LEFT) leftPressed = false;
        if(keyCode == KeyEvent.VK_RIGHT) rightPressed = false;

        if(keyCode == KeyEvent.VK_ESCAPE) escPressed = false;
        if(keyCode == KeyEvent.VK_ENTER) enterPressed = false;
        if(keyCode == KeyEvent.VK_A) aPressed = false;
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H) dPressed = false;
        if(keyCode == KeyEvent.VK_SPACE) spacePressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}