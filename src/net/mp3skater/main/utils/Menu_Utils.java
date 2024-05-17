package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;

import static net.mp3skater.main.GamePanel.isPause;
import static net.mp3skater.main.io.KeyHandler.*;
import static net.mp3skater.main.utils.Sound_Utils.playSE;

public class Menu_Utils {
    public static void update() {

        /*
        Start of the Game, Titlescreen
         */
        if (GamePanel.titleState) {
            // Up
            if (wPressed || upPressed) {
                wPressed = false;
                upPressed = false;
                GamePanel.titleNum--;
                playSE(8);
                if (GamePanel.titleNum < 0) GamePanel.titleNum = 2;
            }
            // Down
            if (sPressed || downPressed) {
                sPressed = false;
                downPressed = false;
                GamePanel.titleNum++;
                playSE(8);
                if (GamePanel.titleNum > 2) GamePanel.titleNum = 0;
            }
            // Confirm choice
            if (enterPressed) {
                enterPressed = false;
                playSE(9);
                if (GamePanel.comandNum == 0) GamePanel.titleState = false; // Start game
                if (GamePanel.titleNum == 1) GamePanel.comandNum = 0; // In future: open highscore file
                if (GamePanel.titleNum == 2) System.exit(0); // Close Game
            }
        }

        /*
        View Controls
         */
        else if (GamePanel.controlState && enterPressed) {
            enterPressed = false;
            GamePanel.controlState = false;
            return;
        }

        /*
        When Player dies
         */
        else if (GamePanel.deathState && enterPressed) {
            System.out.println("lolol");
            enterPressed = false;
            GamePanel.deathState = false;
            GamePanel.titleState=true;
            return;
        }

         /*
        Pause screen
         */
        if (isPause){
            // Up
            if (wPressed || upPressed) {
                wPressed = false;
                upPressed = false;
                GamePanel.pauseNum--;
                playSE(8);
                if (GamePanel.pauseNum < 0) GamePanel.pauseNum = 4;
            }
            // Down
            if (sPressed || downPressed) {
                sPressed = false;
                downPressed = false;
                GamePanel.pauseNum++;
                playSE(8);
                if (GamePanel.pauseNum > 4) GamePanel.pauseNum = 0;
            }
            // Music volume
            if (GamePanel.pauseNum == 0) {
                // Up
                if (aPressed && GamePanel.music.volumeScale > 0) {
                    aPressed = false;
                    GamePanel.music.volumeScale--;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
                // Down
                if (dPressed && GamePanel.music.volumeScale < 5) {
                    dPressed = false;
                    GamePanel.music.volumeScale++;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
            }
            // SE volume
            if (GamePanel.pauseNum == 1) {
                // Up
                if (aPressed && GamePanel.se.volumeScale > 0) {
                    aPressed = false;
                    GamePanel.se.volumeScale--;
                    playSE(8);
                }
                // Down
                if (dPressed && GamePanel.se.volumeScale < 5) {
                    dPressed = false;
                    GamePanel.se.volumeScale++;
                    playSE(8);
                }
            }
            // Confirm choice
            if (enterPressed) {
                enterPressed = false;
                // Controls
                if (GamePanel.pauseNum == 2) GamePanel.controlState = true;
                //
                if (GamePanel.pauseNum == 3) {
                    GamePanel.gameOver(false);
                    GamePanel.titleState = true;
                }
                if (GamePanel.pauseNum == 4) {
                    GamePanel.isPause = false;
                    GamePanel.exPause = true;
                }
            }
        }
    }
}