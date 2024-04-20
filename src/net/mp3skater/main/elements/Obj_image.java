package net.mp3skater.main.elements;

import net.mp3skater.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Obj_image extends Obj {
    public Obj_image(double x, double y, int sX, int sY, Color color, String path) {
        super(x, y, sX, sY, color);
        this.path = path;
    }

    // Path from res
    private final String path;

    @Override
    public void draw(Graphics2D g2) {
        if(collides((int)GamePanel.offset, 0, GamePanel.WIDTH, GamePanel.HEIGHT)) {
            try {
                BufferedImage bufferedImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/forward_down_right.png"));
                g2.drawImage(bufferedImage, (int)(pos[0]+ GamePanel.offset), (int)(pos[1]+GamePanel.offset), size[0], size[1], null);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
