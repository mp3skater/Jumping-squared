package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.obj.Obj;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static net.mp3skater.main.GamePanel.comandNum;

public class Draw_Utils {
    /*
    Globally available Draw-Methods
     */

    /*
    Draws the Titlescreen
     */
    public static void drawTitleScreen(Graphics2D g2) throws IOException, Draw_Utils.BufferedImageGetException {
        // Title Image
        Draw_Utils.drawImage(g2, "/images/Logo.png", new double[]{0,-50}, new int[]{800,350});

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        String text = "New Game";
        int x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,350+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,350);

        if(comandNum==0){
            g2.drawString(">",x-25,350);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,350);
            g2.drawString(text,x,350);
        }

        text = "Highscore";
        x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,425+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,425);

        if(comandNum==1){
            g2.drawString(">",x-25,425);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,425);
            g2.drawString(text,x,425);
        }

        text = "Exit Game";
        x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,500+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,500);

        if(comandNum==2){
            g2.drawString(">",x-25,500);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,500);
            g2.drawString(text,x,500);
        }
    }

    /*
    Draws...
     */
    public static void drawDeathScreen(Graphics2D g2){

    }

    /*
    Draws a rectangle with the given dimensions(<pos>, <size>) and <color>
     */
    public static void fillRect(Graphics2D g2, Obj o, Color color) {
        g2.setColor(color);
        g2.fillRect(o.getDrawX(), (int) o.getY(), (int) o.getSX(), (int) o.getSY());
    }

    /*
    Draws a rectangle with the given dimensions(<pos>, <size>) and <color>
     */
    public static void drawRect(Graphics2D g2, Obj o, Color color) {
        g2.setColor(color);
        g2.drawRect(o.getDrawX(), (int) o.getY(), (int) o.getSX(), (int) o.getSY());
    }

    /*
    Draws an Arrow with fixed direction and size
    It's some complicated maths, magic if you will, don't ask!
     */
    public static void drawArrow(Graphics2D g2, int x, int y, Color color) {
        int length = 200;
        double x2 = x + length;
        int d = (int) (length/3.5), h = (int) (length*0.2);
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
        g2.setStroke(new BasicStroke(length/4f));
        g2.drawLine(x+(length/10), y, x+length-d-10, y);
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
        drawImage(g2, path, new double[]{o.getDrawX(), (int) o.getY()}, new int[]{(int) o.getSX(), (int) o.getSY()});
    }
    public static void drawImage(Graphics2D g2, String path, double[] pos, int[] size) throws BufferedImageGetException {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Draw_Utils.class.getResourceAsStream(path)));
            drawBufferedImage(g2, image, pos, size);
        }
        catch(IOException e) {
            throw new BufferedImageGetException(e);
        }
    }
    private static void drawBufferedImage(Graphics2D g2, BufferedImage bImage, double[] pos, int[] size) {
        g2.drawImage(bImage, (int)(pos[0]+ GamePanel.offset),
                (int)(pos[1]+GamePanel.offset), size[0], size[1], null);
    }

    public static class BufferedImageGetException extends Exception { BufferedImageGetException(Throwable cause)  { super(cause); } }

    public static int getXforCenteredText(Graphics2D g2,String text){
        //Gets the perfect X.Coordinates for the Text position
        int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return GamePanel.WIDTH/2 - textLength/2;
    }
}