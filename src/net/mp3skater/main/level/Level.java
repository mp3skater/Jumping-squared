package net.mp3skater.main.level;

import net.mp3skater.main.elements.Obj;
import net.mp3skater.main.elements.Obj_player;
import net.mp3skater.main.elements.Obj_wall;

import java.awt.*;
import java.util.ArrayList;

public class Level {

    // Individual colors of the level
    private static int[] colPlayer, colWalls, colPlatforms, colText, colArrow, colBG;

    // Player starting position
    private int[] player_pos;

    // Walls (max. 20 walls) [x, y]
    private int[][] walls;

    // Enemies (max. 10 enemies) [x, y]
    private int[][] enemies;

    // Position of texts (max. 5 texts) [x, y]
    private int[][] text_pos;
    // Text value (String) of the texts in the same order
    private String[] text_string;

    // Position of arrows (max. 8 arrows) [x, y]
    private int[][] arrow_pos;

    /*
    Spawns all <Obj>'s in the Arraylist
     */
    //public static void loadLevel(ArrayList<Obj> objs) {
    //    // Spawn
    //    for(int[] w : walls) {
    //        objs.add(new Obj_wall(w[0], w[1], w[2], w[3]));
    //    }
    //}

    /*
    Get the color of a type of <Obj> for the current level
     */
    public Color getColor(String name) {
        return switch (name) {
            case "player" -> new Color(colPlayer[0], colPlayer[1], colPlayer[2]);
            case "wall" -> new Color(colWalls[0], colWalls[1], colWalls[2]);
            case "platform" -> new Color(colPlatforms[0], colPlatforms[1], colPlatforms[2]);
            case "text" -> new Color(colText[0], colText[1], colText[2]);
            case "arrow" -> new Color(colArrow[0], colArrow[1], colArrow[2]);
            case "background" -> new Color(colBG[0], colBG[1], colBG[2]);
            default -> null;
        };
    }

    public void loadLevel(ArrayList<Obj> objs) {
        // Clear all previous <Obj>'s
        objs.clear();

        // Spawn the player
        objs.add(new Obj_player(player_pos[0], player_pos[1], ))
    }
}