package net.mp3skater.main.level;

import net.mp3skater.main.elements.Obj;
import net.mp3skater.main.elements.Obj_player;

import java.awt.*;
import java.util.ArrayList;

public class Level {

    /*
    Giant Constructor
     */
    public Level(int[] colPlayer, int[]colWalls, int[] colPlatforms,
                 int[] colText, int[] colArrow, int[] colBG,
                 int[] player_pos, int[][] walls, int[][] enemies,
                 int[][] text_pos, String[] text_string, int[][] arrow_pos) {
        this.colPlayer = colPlayer;
        this.colWalls = colWalls;
        this.colPlatforms = colPlatforms;
        this.colText = colText;
        this.colArrow = colArrow;
        this.colBG = colBG;
        this.player_pos = player_pos;
        this.walls = walls;
        this.enemies = enemies;
        this.text_pos = text_pos;
        this.text_string = text_string;
        this.arrow_pos = arrow_pos;
    }

    // Individual colors of the level
    private int[] colPlayer, colWalls, colPlatforms, colText, colArrow, colBG;

    // Player starting position [x, y]
    private int[] player_pos;

    // Walls (max. 20 walls) [x, y, sX, sY]
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

    /*
    Returns a new Player with the coordinates from the JSON-file
     */
    public Obj_player getPlayer() {
        return new Obj_player(player_pos[0], player_pos[1]);
        //return new Obj_player(player_pos[0], player_pos[1]);
    }

    /*
    Spawns all <Obj>'s in the given Arraylist
     */
    public void loadLevelObjs(ArrayList<Obj> objs) {
        // Clear all previous <Obj>'s
        objs.clear();

        // Spawn the player
        objs.add(new Obj_player(player_pos[0], player_pos[1]));
    }
}