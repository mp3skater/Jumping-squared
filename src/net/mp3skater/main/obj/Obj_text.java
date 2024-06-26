package net.mp3skater.main.obj;

import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_text extends Obj {
    /*
    Texts that can indicate a new Game mechanic
     */

    public Obj_text(double x, double y, String text) {
        super(x, y, 800, 0, false);
        this.text = text;
    }

    // Text value that gets displayed in-game
    private final String text;

    @Override
    public void draw(Graphics2D g2, Color color) {
        Draw_Utils.drawString(g2, text, new int[]{getDrawX(), (int)pos[1]}, color);
    }
}
