package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;

public class Sound_Utils {
    public static void playMusic(int i) {
        GamePanel.music.setFile(i);
        GamePanel.music.play();
        GamePanel.music.loop();
    }
    public static void stopMusic() {
        GamePanel.music.stop();
    }
    public static void playSE(int i) {

        GamePanel.se.setFile(i);
        GamePanel.se.play();
    }
}
