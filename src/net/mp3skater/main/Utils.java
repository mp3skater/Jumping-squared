package net.mp3skater.main;

import net.mp3skater.main.elements.Element;

import java.awt.*;
import java.util.ArrayList;

public class Utils {

    /*
    Here you can put long code to keep the rest clean.
     */

    // Creates <n> numbers of Elements in the given Arraylist
    public static void setPieces(ArrayList<Element> elements, int n) {
        for(int i = 0; i<n; i++)
            elements.add(randomElement());
    }

    // Random Element
    public static Element randomElement() {
        // Random size from 20 to 50
        int size = (int)(Math.random()*30+20);

        // Random position inside the board
        return new Element(Math.random()*(GamePanel.WIDTH-size), Math.random()*(GamePanel.HEIGHT-size), size,
                // Random color:
                new Color((int)(Math.random() * 0x1000000)));
    }
}