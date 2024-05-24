package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_endBar extends Obj {
    /*
    End of the level
     */

    public Obj_endBar(int length) {
        super(length, 0, GamePanel.WIDTH, GamePanel.HEIGHT, false);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Draw_Utils.fillRect(g2, this, color);
    }
}
