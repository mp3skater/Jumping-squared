package net.mp3skater.main.utils;

import com.google.gson.Gson;
import net.mp3skater.main.Level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level_Utils {
    /*
    Globally available Methods to spawn a level
     */

    /*
    Returns the level with the path: "level/level_<level>.json"
    We're using Google's Gson library that reads the file and creates the Level-class accordingly
     */
    public static Level getLevel(int level) {
        // Get the String value of the json file
        String text = getJsonString(level==2?4:level==4?2:level);
        // Create a new Gson instance
        Gson gson = new Gson();

        /* Test with Level without Json
        Level levl = new Level(new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0},
                new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{100, 100},
                new int[][]{{2, 4, 2, 4}, {2, 3, 2, 4}}, new int[][]{{2, 4}, {2, 3}},
                new int[][]{{2, 4}, {2, 3}}, new String[]{"hello", "lol"}, new int[][]{{2, 4}, {2, 3}});
        String json = gson.toJson(levl);
        System.out.println(json);
         */

        // Returns the class with the elements inside the JSON-file
        return gson.fromJson(text, Level.class);
    }

    /*
    Returns a String with the context of the JSON-file
    No robust logging because it would complicate and not help so much
     */
    private static String getJsonString(int number) {
        // Uses Java-String-Template, similar to pythons f-strings
        String filePath = "res/level/level_"+number+".json";
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n"); // Append each line to the StringBuilder
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}