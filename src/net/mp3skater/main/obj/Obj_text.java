package net.mp3skater.main.obj;

import net.mp3skater.main.Utils;

import java.awt.*;

public class Obj_text extends Obj {
    public Obj_text(double x, double y, String text) {
        super(x, y, 0, 0);
        this.text = text;
    }

    // Text value that gets displayed in-game
    private final String text;

    @Override
    public void draw(Graphics2D g2, Color color) {
        Utils.drawString(g2, text, new int[]{(int)pos[0], (int)pos[1]}, color);
    }
}
