package net.mp3skater.main;

import com.google.gson.Gson;
import net.mp3skater.main.level.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    /*
    Draws a rectangle
     */
    public static void drawRect(Graphics2D g2, double[] pos, int[] size, Color color) {
        g2.setColor(color);
        g2.fillRect((int)pos[0], (int)pos[1], size[0], size[1]);
    }

    /*
    Draws an image
     */
    public static void drawImage(Graphics2D g2, String path, double[] pos, int[] size) {
        try {
            BufferedImage bufferedImage = ImageIO.read(Utils.class.getClassLoader().getResourceAsStream(path));
            g2.drawImage(bufferedImage, (int)(pos[0]+ GamePanel.offset),
                    (int)(pos[1]+GamePanel.offset), size[0], size[1], null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}