package net.mp3skater.main.utils;

import static net.mp3skater.main.GamePanel.*;
import static net.mp3skater.main.io.KeyHandler.*;
import static net.mp3skater.main.utils.Sound_Utils.playSE;

public class Menu_Utils {
    public static void update() {

        /*
        Start of the Game, Titlescreen
         */
        if (titleState) {
            // Up
            if (wPressed || upPressed) {
                wPressed = false;
                upPressed = false;
                titleNum--;
                playSE(8);
                if (titleNum < 0) titleNum = 2;
            }
            // Down
            if (sPressed || downPressed) {
                sPressed = false;
                downPressed = false;
                titleNum++;
                playSE(8);
                if (titleNum > 2) titleNum = 0;
            }
            // Confirm choice
            if (enterPressed) {
                enterPressed = false;
                playSE(9);
                if (comandNum == 0) titleState = false; // Start game
                if (titleNum == 1) comandNum = 0; // In future: open highscore file
                if (titleNum == 2) System.exit(0); // Close Game
            }
        }

        /*
        View Controls
         */
        else if (controlState && enterPressed) {
            enterPressed = false;
            controlState = false;
            pauseState = true;
            return;
        }

        /*
        When Player dies
         */
        else if (deathState && enterPressed) {
            enterPressed = false;
            deathState = false;
            titleState = true;
            return;
        }

        /*
        When Player dies
         */
        else if (winState && enterPressed) {
            enterPressed = false;
            winState = false;
            titleState = true;
            return;
        }

        /*
        Pause screen
         */
        if (pauseState){
            // Up
            if (wPressed || upPressed) {
                wPressed = false;
                upPressed = false;
                pauseNum--;
                playSE(8);
                if (pauseNum < 0) pauseNum = 4;
            }
            // Down
            if (sPressed || downPressed) {
                sPressed = false;
                downPressed = false;
                pauseNum++;
                playSE(8);
                if (pauseNum > 4) pauseNum = 0;
            }
            // Music volume
            if (pauseNum == 0) {
                // Up
                if ((aPressed || leftPressed) && music.volumeScale > 0) {
                    aPressed = false;
                    music.volumeScale--;
                    music.checkVolume();
                    playSE(8);
                }
                // Down
                if ((dPressed || rightPressed) && music.volumeScale < 5) {
                    dPressed = false;
                    music.volumeScale++;
                    music.checkVolume();
                    playSE(8);
                }
            }
            // SE volume
            if (pauseNum == 1) {
                // Up
                if ((aPressed || leftPressed)  && se.volumeScale > 0) {
                    aPressed = false;
                    leftPressed = false;
                    se.volumeScale--;
                    playSE(8);
                }
                // Down
                if ((dPressed || rightPressed) && se.volumeScale < 5) {
                    dPressed = false;
                    rightPressed = false;
                    se.volumeScale++;
                    playSE(8);
                }
            }
            // Confirm choice
            if (enterPressed) {
                enterPressed = false;
                // Controls
                if (pauseNum == 2) {
                    controlState = true;
                    pauseState = false;
                }
                // Endgame
                //if (pauseNum == 3) {
                //    gameOver(false);
                //    titleState=true;
                //}
                if (pauseNum == 4) {
                    pauseState = false;
                    exPause = true;
                }
            }
        }
    }
}