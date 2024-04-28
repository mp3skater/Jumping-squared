package net.mp3skater.main;

import net.mp3skater.main.obj.*;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	// Booleans for the pause-function
	private boolean exPause = true; // To see if Pause has been changed
	private boolean isPause = true;
	private static boolean activatePause = false;

	// High-score
	private static int highscore = -1;

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

	// Levels 1-5
	public static int level = 0;
	private static Level currentLevel;
	public static int newGame = 2;

	// Time (in frames, 60 = 1 sec)
	private static int time = 0;

	// Offset (moves horizontally with the player)
	public static double offset = 0;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

		// Implement KeyListener:
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
	}

	/*
	Move Offset with the player, so you can see what's happening
	Its one-directional like super mario bros 1
	 */
	public static void increaseOffset(int increase) {
		offset += increase;
	}

	/*
	Loads the next level, incrementing <level>
	If it was the last level the game is over
	 */
	public static void loadNextLevel() {
		if(level==3)
			gameWon();
		else {
			delay = -50;
			offset = 0;
			level++;
			currentLevel = Utils.getLevel(level);
			player = currentLevel.getPlayer();
			currentLevel.loadLevelObjs(walls, enemies, texts, arrows);
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
		newGame = 2;
		activatePause = true;
		time = -1; // It updates the time once, so this sets it to 0 essentially
		level = 0;
		loadNextLevel();
	}

	/*
	Gets called when the player dies
	Sets pause to true and loads the first level
	 */
	public static void gameWon() {
		if(highscore == -1 || time < highscore) {
			System.out.println(STR."NEW HIGHSCORE: \{time}");
			highscore = time;
		}
		System.out.println(STR."Game won, time = \{time/60} sec. / \{time} frames");
		// Insert code
		gameOver();
	}

	/*
	Method that gets called from <main.Main>
	 */
	public void launchGame() {
		// Get the first level, spawn the player and all other <Obj>'s in <objs>
		loadNextLevel();
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
	Changes the current pause state
	 */
	private void changePauseState() {
		isPause = !isPause;
	}

	/*

	 */
	private void checkPlatforms() {
		aimPlatform.setPos((int)(mouse.x-(aimPlatform.getSX()/2)+offset),
				(int)(mouse.y-(aimPlatform.getSY()/2)));

		if(newGame>0)
			clearPlatforms();

		if(mouse.pressed && delay+20<time && !player.collides(aimPlatform)) {
			platforms[1] = platforms[0];
			platforms[0] = aimPlatform.neu();
			delay = time;
		}
	}

	/*
	Update method for player, enemies...
	 */
	private void update() {

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
		if(newGame >= 0)
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
		Font font = new Font ("OpenSymbol", Font.BOLD, 30);
		g2.setFont(font);

		// Board
		drawBoard(g2);

		// <Obj>'s
		paintObjs(g2);

		// Time
		font = new Font ("OpenSymbol", Font.BOLD, 10);
		g2.setFont(font);
		g2.drawString(STR."Time: \{time}", 10, 15);

		// Pause (needs to be arranged to the center if you change WIDTH or HEIGHT)
		if(isPause) {
			g2.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			g2.fillRect(0,0,WIDTH,HEIGHT);
		}

		if(newGame > 0) {
			// Background
			g2.setColor(Color.black);
			g2.fillRect(0,0,WIDTH,HEIGHT);

			g2.setColor(Color.white);
			font = new Font ("OpenSymbol", Font.BOLD, 50);
			g2.setFont(font);
			g2.drawString("Press ENTER to Start", 140, 300);
		}
	}
}