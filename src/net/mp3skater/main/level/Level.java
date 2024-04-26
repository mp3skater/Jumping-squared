package net.mp3skater.main.level;

import net.mp3skater.main.obj.*;

import java.awt.*;
import java.util.ArrayList;

public class Level {

    /*
    Giant Constructor, mostly for testing purposes
     */
    public Level(int[] colPlayer, int[] colEnemies, int[]colWalls, int[] colPlatforms,
                 int[] colText, int[] colArrow, int[] colBG, int length, int[] colEnd,
                 int[] player_pos, int[][] walls, int[][] enemies,
                 int[][] text_pos, String[] text_string, int[][] arrow_pos) {
        this.colPlayer = colPlayer;
        this.colEnemies = colEnemies;
        this.colWalls = colWalls;
        this.colPlatforms = colPlatforms;
        this.colText = colText;
        this.colArrow = colArrow;
        this.colBG = colBG;
        this.length = length;
        this.colEnd = colEnd;
        this.player_pos = player_pos;
        this.walls = walls;
        this.enemies = enemies;
        this.text_pos = text_pos;
        this.text_string = text_string;
        this.arrow_pos = arrow_pos;
    }

    /*
    Info:
    negative values in the y direction or x > <GamePanel.HEIGHT> will not make a difference they are unreachable
    negative in the x direction will be invisible, but they still have impact with the game
     */

    // Length of the level
    private final int length;

    // Individual colors of the level
    private final int[] colPlayer, colEnemies, colWalls, colPlatforms, colText, colArrow, colBG, colEnd;

    // Player starting position [x, y]
    private final int[] player_pos;

    // Walls (max. 20 walls) [x, y, sX, sY]
    private final int[][] walls;

    // Enemies (max. 10 enemies) [x, y]
    private final int[][] enemies;

    // Position of texts (max. 5 texts) [x, y]
    private final int[][] text_pos;
    // Text value (String) of the texts in the same order
    private final String[] text_string;

    // Position of arrows (max. 8 arrows) [x, y]
    private final int[][] arrow_pos;

    /*
    Get the color of a type of <Obj> for the current level
     */
    public Color getColor(String name) {
        switch (name) {
            case "player" -> { return new Color(colPlayer[0], colPlayer[1], colPlayer[2]); }
            case "enemy" -> { return new Color(colEnemies[0], colEnemies[1], colEnemies[2]); }
            case "wall" -> { return new Color(colWalls[0], colWalls[1], colWalls[2]); }
            case "platform" -> { return new Color(colPlatforms[0], colPlatforms[1], colPlatforms[2]); }
            case "text" -> { return new Color(colText[0], colText[1], colText[2]); }
            case "arrow" -> { return new Color(colArrow[0], colArrow[1], colArrow[2]); }
            case "background" -> { return new Color(colBG[0], colBG[1], colBG[2]); }
            case "endbar" -> { return new Color(colEnd[0], colEnd[1], colEnd[2]); }
            default -> { System.out.println(STR."Color \"\{name}\" not found."); return null; }
        }
    }

    /*
    Returns a new Player with the coordinates from the JSON-file
     */
    public Obj_player getPlayer() {
        return new Obj_player(player_pos[0], player_pos[1]);
    }

    /*
    Spawns all <Obj>'s in the given Arraylist
     */
    public void loadLevelObjs(ArrayList<Obj> objs) {
        // Clear all previous <Obj>'s
        objs.clear();

        for(int[] w : walls)
            objs.add(new Obj_wall(w[0], w[1], w[2], w[3]));
        for(int[] e : enemies)
            objs.add(new Obj_enemy(e[0], e[1]));

        // Spawn the "End-Bar"
        objs.add(new Obj_endBar(length));
    }
}