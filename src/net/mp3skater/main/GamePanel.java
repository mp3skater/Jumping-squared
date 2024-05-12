package net.mp3skater.main;

import net.mp3skater.main.io.Sound;
import net.mp3skater.main.obj.*;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;
import net.mp3skater.main.utils.Draw_Utils;
import net.mp3skater.main.utils.Level_Utils;
import net.mp3skater.main.utils.Sound_Utils;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static net.mp3skater.main.utils.Draw_Utils.drawTitleScreen;
import static net.mp3skater.main.utils.Sound_Utils.*;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	//SOUND
    public static Sound music = new Sound();
	public static Sound se = new Sound();
	private static int currentMusic = -1;

	// Font Styles
	Font maruMonica;

	// Game State Screens
	public static boolean titleState, deathState;
	public static int comandNum =0,titleNum =0, deathNum =0, pauseNum =0;
	public static int framesCounter =0;


	// Booleans for the pause-function
	public static boolean isPause = true, exPause = true; // To see if Pause has been changed
	private static boolean activatePause = false;

	// High-score
	private static int highscore = -1;

	// Won (Amogus skin)
	public static boolean won = false;

	// <Obj>'s
	private static Obj_player player;
	public static ArrayList<Obj> walls = new ArrayList<>();
	public static ArrayList<Obj_enemy> enemies = new ArrayList<>();
	public static ArrayList<Obj_text> texts = new ArrayList<>();
	public static ArrayList<Obj_arrow> arrows = new ArrayList<>();

	// Platforms
	// x:-100,y:-100 means if the game is currently paused
	private static final Obj_platform aimPlatform = new Obj_platform(0,0);
	public static Obj_platform[] platforms = new Obj_platform[2];
	private static int delay = -50;
	private boolean exMClicked = false; // So no player can spam platforms by leaving the mouse pressed

	// Levels 1-5
	public static int level = 1;
	private static Level currentLevel;
	public static int newGame = 2;

	// Time (in frames, 60 = 1 sec)
	private static int time = 0;

	// Offset (moves horizontally with the player)
	public static double offset = 0;

	public GamePanel() throws IOException, FontFormatException {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

		InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
        assert is != null;
        maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

		// Implement KeyListener:
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
	}

	/*
	Method that gets called from <main.Main>
	 */
	public void launchGame() {
		// Get the first level, spawn the player and all other <Obj>'s in <objs>
		loadLevel(level);
		// Start the thread to start the Game loop
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// GAME LOOP
		double drawInterval = 1_000_000_000d/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while(gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime-lastTime)/drawInterval;
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	/*
	Returns the color from the current level
	 */
	public static Color getColor(String name) {
		return currentLevel.getColor(name);
	}

	/*
	Loads the next level, incrementing <level>
	If it was the last level the game is over
	 */
	public static void loadLevel(int level) {
		if(level == 5)
			gameWon();
		else {
			clearPlatforms();
			delay = -50;
			offset = 0;
			currentLevel = Level_Utils.getLevel(level);
			player = currentLevel.getPlayer();
			currentLevel.loadLevelObjs(walls, enemies, texts, arrows);
			musicUpdate();
		}
	}

	/*
	Starts to play the new music of the level, if it's the same then not
	 */
	private static void musicUpdate() {
		// If the current level has different music: change music
		if(currentLevel.getMusic() != currentMusic) {
			music.stop();
			Sound_Utils.playMusic(currentLevel.getMusic());
			currentMusic = currentLevel.getMusic();
			return;
		}
		if(currentMusic == -1) {
			Sound_Utils.playMusic(currentLevel.getMusic());
			currentMusic = currentLevel.getMusic();
		}
	}

	/*
	Clears all platforms from <platforms>
	 */
	public static void clearPlatforms() {
        Arrays.fill(platforms, null);
	}

	/*
	Returns the length of the current level
	 */
	public static int getLength() {
		return currentLevel.getLength();
	}

	/*
	Gets called when the player dies
	Sets pause to true and loads the first level
	 */
	public static void gameOver() {
		playSE(5);
		stopMusic();
		newGame = 2;
		activatePause = true;
		time = -1; // It updates the time once, so this sets it to 0 essentially
		level = 1;
		currentMusic = -1;
		loadLevel(level);
	}

	/*
	Gets called when the player dies
	Sets pause to true and loads the first level
	 */
	public static void gameWon() {
		won = true;
		if(highscore == -1 || time < highscore) {
			playSE(6);
			System.out.println("NEW HIGHSCORE: "+time);
			highscore = time;
		}
		System.out.println("Game won, time = "+time/60+" sec. / "+time+" frames");
		// Insert code
		gameOver();
	}

	/*
	Changes the current pause state
	 */
	private void changePauseState() {
		isPause = !isPause;
	}

	/*
	Spawn new Platforms and update the pos of <aimPlatform>
	 */
	private void checkPlatforms() {

		aimPlatform.setPos((int)(mouse.x-(aimPlatform.getSX()/2)+offset),
				(int)(mouse.y-(aimPlatform.getSY()/2)));

		if(newGame>0)
			clearPlatforms();

		if(mouse.pressed && !exMClicked && delay+20<time && !player.collides(aimPlatform)) {

			platforms[1] = platforms[0];
			platforms[0] = aimPlatform.neu();
			delay = time;
		}
		exMClicked = mouse.pressed;
	}

	private void focusCam() {
		offset = player.getX()-GamePanel.WIDTH/2.0;
	}

	/*
	Update method for player, enemies...
	 */
	private void update() {

		// Uncode this to get a log of your game...
		//System.out.println((int)player.getX()+", "+(int)player.getY()+
				//", nG = "+newGame+", vec: "+player.getVX()+", "+player.getVY());

		// Devtool
		// Move when paused
		if(GamePanel.isPause && newGame == 0) {
			if(KeyHandler.aPressed || KeyHandler.leftPressed) player.addX(-10); focusCam();
			if(KeyHandler.dPressed || KeyHandler.rightPressed) player.addX(10); focusCam();
			if(KeyHandler.upPressed) player.addY(-10); focusCam();
			if(KeyHandler.downPressed) player.addY(10); focusCam();
		}

		// Pause, when pause is being pressed
		if(KeyHandler.pausePressed && !exPause)
			changePauseState();
		exPause = KeyHandler.pausePressed;

		// Don't continue if Game paused
		if(isPause)
			return;

		// Change Pause
		if(activatePause) {
			isPause = true;
			activatePause = false;
		}

		// Change newGame
		if(newGame > 0)
			newGame--;

		// Update time
		time++;

		// Update Player position
		player.update();

		// Update the set
		checkPlatforms();

		// Update the Enemy AI
		for(Obj_enemy e : enemies)
			e.update();
	}

	/*
	Draws all <Obj>'s using the according color template of the level
	In a specific order, DO NOT CHANGE!
	 */
	private void paintObjs(Graphics2D g2) {
		if(currentLevel == null)
			return;

		// Arrows and texts
		for(Obj_arrow a : arrows)
			a.draw(g2, currentLevel.getColor("arrow"));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		for(Obj_text t : texts)
			t.draw(g2, currentLevel.getColor("text"));

		// Walls and Endbar
		for(Obj o : walls) {
			if (o instanceof Obj_wall w) w.draw(g2, currentLevel.getColor("wall"));
			if (o instanceof Obj_endBar bar) bar.draw(g2, currentLevel.getColor("endbar"));
		}

		// Platforms
		for(Obj_platform p : platforms)
			if(p != null)
				p.draw(g2, currentLevel.getColor("platform"));
		if(!isPause) {
			g2.setStroke(new BasicStroke(5));
			if(aimPlatform != null)
				if(!player.collides(aimPlatform))
					aimPlatform.drawAim(g2, currentLevel.getColor("platform"));
		}

		// Enemies
		for(Obj_enemy e : enemies)
			e.draw(g2, currentLevel.getColor("enemy"));

		// Player
		if(player != null && player.is_drawable())
			player.draw(g2, currentLevel.getColor("player"));
	}

	/*
	Draws the background using the <board>-class
	 */
	private void drawBoard(Graphics2D g2) {
		if(currentLevel != null)
			board.draw(g2, currentLevel);
	}

	/*
	Gets activated with <repaint();>
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// Set a font (example)
		g2.setColor(Color.white);
		g2.setFont(maruMonica);

		//TITLE SCREEN View
		if(titleState){
            try {
                drawTitleScreen(g2);
            } catch (IOException | Draw_Utils.BufferedImageGetException e) {
                throw new RuntimeException(e);
            }
			return;
        }

		// Game not paused:
		// Board
		drawBoard(g2);
		// <Obj>'s
		paintObjs(g2);
		// Time
		g2.setColor(currentLevel.getColor("text"));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,20f));
		g2.drawString("Time: "+time, 5, 20);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
		// Pause (needs to be arranged to the center if you change WIDTH or HEIGHT)
		if(isPause) {
			Draw_Utils.drawPauseScreen(g2);
		}

//		if(newGame > 0) {
//			// Background
//			g2.setColor(Color.black);
//			g2.fillRect(0,0,WIDTH,HEIGHT);
//
//			g2.setColor(Color.white);
//			font = new Font ("Century", Font.BOLD, 50);
//			g2.setFont(font);
//			g2.drawString("Press ENTER to Start", 140, 300);
//		}
	}
}