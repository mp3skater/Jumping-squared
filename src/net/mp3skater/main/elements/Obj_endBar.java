package net.mp3skater.main.elements;

import net.mp3skater.main.GamePanel;

import java.awt.*;

public class Obj_endBar extends Obj {
    public Obj_endBar(int length) {
        super(length, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fillRect((int)pos[0], (int)pos[1], size[0], size[1]);
    }
}
