package net.mp3skater.main.util;

import com.google.gson.Gson;
import net.mp3skater.main.GamePanel;
import net.mp3skater.main.level.Level;
import net.mp3skater.main.obj.Obj;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Utils {

    /*
    Returns the level with the path: "level/level_<level>.json"
    We're using Google's Gson library that reads the file and creates the Level-class accordingly
     */
    public static Level getLevel(int level) {
        // Get the String value of the json file
        String text = getJsonString(level);
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
        String filePath = STR."res/level/level_\{number}.json";
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
    Draws a rectangle with the given dimensions(<pos>, <size>) and <color>
     */
    public static void drawRect(Graphics2D g2, Obj o, Color color) {
        g2.setColor(color);
        g2.fillRect(o.getDrawX(o), (int) o.getY(), (int) o.getSX(), (int) o.getSY());
    }

    /*
    Draws an image. IDK how, but I tried to make it more "protected" using robust logging
     */
    public static void drawImage(Graphics2D g2, String path, Obj o) throws BufferedImageGetException {
        try {
            BufferedImage bImage = ImageIO.read(Objects.requireNonNull(Utils.class.getClassLoader().getResourceAsStream(path)));
            drawBufferedImage(g2, bImage, new double[]{o.getDrawX(o), (int)o.getY()}, new int[]{(int) o.getSX(), (int) o.getSY()});
        }
        catch(BufferedImageDrawException | IOException e) {
            throw new BufferedImageGetException(e);
        }
    }
    private static void drawBufferedImage(Graphics2D g2, BufferedImage bImage, double[] pos, int[] size) throws BufferedImageDrawException {
        g2.drawImage(bImage, (int)(pos[0]+ GamePanel.offset),
                (int)(pos[1]+GamePanel.offset), size[0], size[1], null);
    }
    public static class BufferedImageGetException extends Exception { BufferedImageGetException(Throwable cause)  { super(cause); } }
    public static class BufferedImageDrawException extends Exception {}
}