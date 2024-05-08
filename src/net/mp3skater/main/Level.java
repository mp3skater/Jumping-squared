package net.mp3skater.main;

import net.mp3skater.main.obj.*;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    /*
    Has arrays with all elements of the level
    All values get inserted with the json files in: "res/level"
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
    negative values in the x/y direction or x > <GamePanel.HEIGHT> will not make a difference, they are unreachable
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
    Returns the length of the level
     */
    public int getLength() {
        return length;
    }

    /*
    Spawns all <Obj>'s in the given Arraylist
     */
    public void loadLevelObjs(ArrayList<Obj> walls, ArrayList<Obj_enemy> enemies, ArrayList<Obj_text> texts, ArrayList<Obj_arrow> arrows) {
        // Clear all previous <Obj>'s
        walls.clear();
        enemies.clear();
        texts.clear();
        arrows.clear();

        for(int[] w : this.walls)
            walls.add(new Obj_wall(w[0], w[1], w[2], w[3]));
        for(int[] e : this.enemies)
            enemies.add(new Obj_enemy(e[0], e[1], e[2]));
        assert text_pos.length == text_string.length;
        for(int i = 0; i<text_pos.length; i++)
            texts.add(new Obj_text(text_pos[i][0], text_pos[i][1], text_string[i]));
        for(int[] a : this.arrow_pos)
            arrows.add(new Obj_arrow(a[0], a[1]));

        // Spawn the "End-Bar"
        walls.add(new Obj_endBar(length));
    }
}