package net.mp3skater.main;

import net.mp3skater.main.obj.*;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

	// <Obj>'s
	private static Obj_player player;
	public static ArrayList<Obj> walls = new ArrayList<>();
	public static ArrayList<Obj_enemy> enemies = new ArrayList<>();
	public static ArrayList<Obj_text> texts = new ArrayList<>();
	public static ArrayList<Obj_arrow> arrows = new ArrayList<>();

	// Levels 1-5
	public static int level = 0;
	private static Level currentLevel;

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
	Spawn a new Level with the number <level>
	 */
	public static void loadNextLevel() {
		offset = 0;
		level++;
		currentLevel = Utils.getLevel(level);
		player = currentLevel.getPlayer();
		currentLevel.loadLevelObjs(walls, enemies, texts, arrows);
	}

	public static int getLength() {
		return currentLevel.getLength();
	}

	public static void gameOver() {
		activatePause = true;
		time = -1; // It updates the time once, so this sets it to 0 essentially
		level = 0;
		loadNextLevel();
	}

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
	private void changePauseState() {
		isPause = !isPause;
	}
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

		// Update time
		time++;

		// Update Player position
		player.update();

		// Update the Enemy AI
		for(Obj_enemy e : enemies)
			e.update();

		//

		// Change level to show all colors ///// NOT LATER IN THE GAME JUST FOR TESTING
		//if(time%100==0) {
		//	if(level!=5)
		//		level++;
		//	else
		//		level=1;
		//}
	}

	/*
	Draws all <Obj>'s using the according color template of the level
	In a specific order, DO NOT CHANGE!
	 */
	private void paintObjs(Graphics2D g2) {
		// Walls + Enemies + Texts + Arrows
		for(Obj o : walls) {
			if (o instanceof Obj_wall w) w.draw(g2, currentLevel.getColor("wall"));
			if (o instanceof Obj_endBar bar) bar.draw(g2, currentLevel.getColor("endbar"));
		}
		for(Obj_arrow a : arrows)
			a.draw(g2, currentLevel.getColor("arrow"));
		for(Obj_enemy e : enemies)
			e.draw(g2, currentLevel.getColor("enemy"));

		// Player
		if(player != null && player.is_drawable())
			player.draw(g2, currentLevel.getColor("player"));

	}
	private void drawBoard(Graphics2D g2) {
		if(currentLevel != null)
			board.draw(g2, currentLevel);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// Board
		drawBoard(g2);

		// <Obj>'s
		paintObjs(g2);

		// Set a font (example)
		g2.setColor(Color.white);
		Font font = new Font ("Courier New", Font.BOLD, 10);
		g2.setFont(font);

		// Time
		g2.drawString(STR."\{time}", 10, 15);

		// Pause (needs to be arranged to the center if you change WIDTH or HEIGHT)
		if(isPause) {
			g2.setColor(Color.blue);
			g2.setFont(new Font ("Courier New", Font.BOLD, 50));
			g2.drawString("Game Paused", 230, 310);
		}
	}
}