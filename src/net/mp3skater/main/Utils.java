package net.mp3skater.main;

import com.google.gson.Gson;
import net.mp3skater.main.elements.Obj;
import net.mp3skater.main.level.Level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    /*
    Spawn all walls and other <Obj>'s from the according json file
    Using Google's Gson library I read the file and create the <level>-class accordingly
     */
    public static void spawnlevel(int number) {
        // Get the String value of the json file
        String text = getJsonString(number);
        // Create a new <Gson> instance
        Gson gson = new Gson();
        // Fill in the class parameter from the json file
        Level level = gson.fromJson(text, Level.class);

        System.out.println(level);
        
        // Spawn all <Obj>'s from the class
        //spawnObjs(level);
    }

    //private void spawnObjs(Level level) {
    //    for(Obj o : level.)
    //}

    private static String getJsonString(int number) {
        String filePath = "res/level/level_" + number + ".json";
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