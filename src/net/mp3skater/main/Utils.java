package net.mp3skater.main;

import com.google.gson.Gson;
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
    Draws an Arrow with fixed direction and size
    It's some complicated maths, magic if you will, don't ask!
     */
    public static void drawArrow(Graphics2D g2, int x, int y, Color color) {
        int length = 200;
        double x2 = x + length;
        int d = length/2, h = (int) (length*0.3);
        int dx = (int)(x2 - x), dy = (int)((double)y - y);
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x1;
        double sin = dy / D, cos = dx / D;

        x1 = xm*cos - ym*sin + x;
        ym = xm*sin + ym*cos + y;
        xm = x1;

        x1 = xn*cos - yn*sin + x;
        yn = xn*sin + yn*cos + y;
        xn = x1;

        int[] xpoints = {(int)x2, (int)xm, (int)xn};
        int[] ypoints = {(int) (double)y, (int)ym, (int)yn};

        g2.setColor(color);
        g2.setStroke(new BasicStroke(length/5f));
        g2.drawLine(x+(length/10), y, x+length-d, y);
        g2.fillPolygon(xpoints, ypoints, 3);
    }

    /*
    Draws the <text>-value of a text inside the game
    Don't forget to set the font/size
     */
    public static void drawString(Graphics2D g2, String text, int[] pos, Color color) {
        g2.setColor(color);
        g2.drawString(text, pos[0], pos[1]);
    }

    /*
    Draws an image. IDK how, but I tried to make it more "protected" using robust logging
     */
    public static void drawImage(Graphics2D g2, String path, Obj o) throws BufferedImageGetException {
        drawImage(g2, path, new double[]{o.getDrawX(o), (int) o.getY()}, new int[]{(int) o.getSX(), (int) o.getSY()});
    }
    public static void drawImage(Graphics2D g2, String path, double[] pos, int[] size) throws BufferedImageGetException {
        try {
            BufferedImage bImage = ImageIO.read(Objects.requireNonNull(Utils.class.getClassLoader().getResourceAsStream(path)));
            drawBufferedImage(g2, bImage, pos, size);
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