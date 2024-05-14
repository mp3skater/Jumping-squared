package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import static net.mp3skater.main.utils.Sound_Utils.playSE;

public class KeyHandler implements KeyListener {
    /*
    This class handles the key presses and stores them in global variables
     */

    public static boolean pausePressed, aPressed, dPressed, spacePressed, zeroPressed, onePressed,
            twoPressed, threePressed, fourPressed,fivePressed, upPressed, downPressed, leftPressed, rightPressed,
            backPressed,backControlPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(GamePanel.titleState){
            if(keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) {
                GamePanel.titleNum--;
                playSE(8);
                if (GamePanel.titleNum < 0) {
                    GamePanel.titleNum = 2;
                }
            }
            if(keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
                GamePanel.titleNum++;
                playSE(8);
                if (GamePanel.titleNum > 2) {
                    GamePanel.titleNum = 0;
                }
            }
            if(keyCode ==KeyEvent.VK_ENTER){
                playSE(9);
                if(GamePanel.comandNum==0){
                    GamePanel.titleState=false;
                }
                if(GamePanel.titleNum==1){
                    GamePanel.comandNum=0;
                    //Open Records File
                }
                if(GamePanel.titleNum==2)
                    System.exit(0);
            }
        }
        if(GamePanel.isPause) {
            if(GamePanel.controlState){
                if (keyCode==KeyEvent.VK_ENTER){
                    GamePanel.controlState=false;
                }
            }
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                GamePanel.pauseNum--;
                playSE(8);
                if (GamePanel.pauseNum < 0) {
                    GamePanel.pauseNum = 4;
                }
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                GamePanel.pauseNum++;
                playSE(8);
                if (GamePanel.pauseNum > 4) {
                    GamePanel.pauseNum = 0;
                }
            }
            //MUSIC VOLUME
            if(GamePanel.pauseNum==0){
                if(keyCode == KeyEvent.VK_A&&GamePanel.music.volumeScale>0){
                    GamePanel.music.volumeScale--;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
                if(keyCode == KeyEvent.VK_D&&GamePanel.music.volumeScale<5){
                    GamePanel.music.volumeScale++;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
            }
            //SE VOLUME
            if(GamePanel.pauseNum==1){
                if(keyCode == KeyEvent.VK_A&&GamePanel.se.volumeScale>0){
                    GamePanel.se.volumeScale--;
                    playSE(8);
                }
                if(keyCode == KeyEvent.VK_D&&GamePanel.se.volumeScale<5){
                    GamePanel.se.volumeScale++;
                    playSE(8);
                }
            }
            //Controls
            if (keyCode == KeyEvent.VK_ENTER) {
                if (GamePanel.pauseNum == 2) {

                    if (keyCode==KeyEvent.VK_ENTER){
                        GamePanel.controlState=true;
                    }
                }
                if (GamePanel.pauseNum == 3) {

                    //End Game
                }
                if (GamePanel.pauseNum == 4) {
                    GamePanel.isPause=false;
                    GamePanel.exPause=true;
                }
            }
        }
        if (GamePanel.deathState) {
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                GamePanel.comandNum--;
                if (GamePanel.deathNum < 0) {
                    GamePanel.deathNum = 1;
                }
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                GamePanel.comandNum++;
                if (GamePanel.deathNum > 1) {
                    GamePanel.deathNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                if (GamePanel.deathNum == 0) {
                    GamePanel.deathState = false;
                    GamePanel.gameOver();
                }
                if (GamePanel.deathNum == 1) {
                    GamePanel.deathState = false;
                    GamePanel.deathNum = 0;
                    GamePanel.titleState = true;
                }
            }
        }



        // Dev tools
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = true;
        if(keyCode == KeyEvent.VK_1)
            onePressed = true;
        if(keyCode == KeyEvent.VK_2)
            twoPressed = true;
        if(keyCode == KeyEvent.VK_3)
            threePressed = true;
        if(keyCode == KeyEvent.VK_4)
            fourPressed = true;
        if(keyCode == KeyEvent.VK_5)
            fivePressed = true;
        if(keyCode == KeyEvent.VK_UP)
            upPressed = true;
        if(keyCode == KeyEvent.VK_DOWN)
            downPressed = true;
        if(keyCode == KeyEvent.VK_LEFT)
            leftPressed = true;
        if(keyCode == KeyEvent.VK_RIGHT)
            rightPressed = true;

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

        // Dev tools
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = false;
        if(keyCode == KeyEvent.VK_1)
            onePressed = false;
        if(keyCode == KeyEvent.VK_2)
            twoPressed = false;
        if(keyCode == KeyEvent.VK_3)
            threePressed = false;
        if(keyCode == KeyEvent.VK_4)
            fourPressed = false;
        if(keyCode == KeyEvent.VK_5)
            fivePressed = false;
        if(keyCode == KeyEvent.VK_UP)
            upPressed = false;
        if(keyCode == KeyEvent.VK_DOWN)
            downPressed = false;
        if(keyCode == KeyEvent.VK_LEFT)
            leftPressed = false;
        if(keyCode == KeyEvent.VK_RIGHT)
            rightPressed = false;

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