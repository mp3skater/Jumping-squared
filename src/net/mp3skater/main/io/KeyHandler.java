package net.mp3skater.main.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    /*
    This class handles the key presses and stores them in global variables.
     */

    public static boolean pausePressed, aPressed, dPressed, spacePressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE)
            pausePressed = true;

        if(keyCode == KeyEvent.VK_A)
            aPressed = true;
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H)
            dPressed = true;
        if(keyCode == KeyEvent.VK_SPACE)
            spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE)
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