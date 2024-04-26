package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.util.Utils;

import java.awt.*;

public class Obj_endBar extends Obj {
    public Obj_endBar(int length) {
        super(length, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Utils.drawRect(g2, this, color);
    }
}
