package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.mp3skater.main.GamePanel.*;
import static net.mp3skater.main.utils.Sound_Utils.playSE;

public class Misc_Utils {

    /*
    Returns an int that is rounded up away from 0
     */
    public static int absRound(double number) {
        return number > 0 ? (int) Math.ceil(number) : (int) Math.floor(number);
    }

    /*
    Gets called when the player finishes all levels
    If it's a new highscore, the highscore gets saved in a text file
    That file will be created the first time the game gets played through
     */
    public static void gameWon() {
        int highscore = 1000000000; // Takes 192+ days, pretty shit highscore huh
        if(winState) return; // To avoid multiple gameWon for whatever reason
        winState = true; // For the win screen
        GamePanel.won = true; // For amogus
        playSE(6); // Won sound effect

        timeTemp = time; // To not lose the time
        System.out.println("Game won, time: " + timeTemp);

        String path = "res/highscores.txt";
        Path stringP = Paths.get(path);
        File scores = new File(path);
        try {
            // Creating file, if necessary
            if(scores.createNewFile()) System.out.println("Creating new file: " + path);

            // For the right order the highscores are written in
            String file = "";

            // Getting the current highscore
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                // Get the file
                try {
                    file = String.join("\n", Files.lines(stringP).toArray(String[]::new));
                } catch(Exception e) {
                    e.printStackTrace();
                }
                String firstLine = reader.readLine(); // Read the first line
                try {
                    highscore = Integer.parseInt(firstLine);
                } catch (Exception _) {
                    highscore = 1000000000;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }

            // Storing highscore
            if(timeTemp < highscore) {
                System.out.println("Highscore is being stored in " + path);
                try(PrintWriter writer = new PrintWriter(path)) {
                    writer.println(timeTemp+"\n"+file);
                } catch (IOException e) {
                    System.out.println("Could not write to file :"+path);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Problems while reading/creating the file \"res/info/highscores.txt\"");
            e.printStackTrace();
        }
        gameOver(false);
    }
}
