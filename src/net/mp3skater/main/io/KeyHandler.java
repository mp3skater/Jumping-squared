package net.mp3skater.main.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    /*
    To use a new button: replace {button} and {BUTTON} with the key of your choice.
     */

    public static boolean pausePressed;

    //public static boolean {button}Pressed

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_ESCAPE)
            pausePressed = true;

        //if(keyCode == KeyEvent.VK_{BUTTON})
        //    {button}Pressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_ESCAPE)
            pausePressed = false;

        //if(keyCode == KeyEvent.VK_{BUTTON})
        //    {button}Pressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}