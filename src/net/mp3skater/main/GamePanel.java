package net.mp3skater.main;

import net.mp3skater.main.elements.Obj;
import net.mp3skater.main.elements.Obj_player;
import net.mp3skater.main.elements.Obj_wall;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;
import net.mp3skater.main.level.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	// Booleans for the pause-function
	private boolean exPause = true; // To see if Pause has been changed
	private boolean isPause = true;

	// <Obj>'s
	private static Obj_player player;
	public static ArrayList<Obj> objs = new ArrayList<>();

	// Level 1-5
	private static int level = 1;
	private static Level currentLevel;

	// Time (in frames, 60 = 1 sec)
	private static int time = 0;

	// Offset (moves horizontally with the <Player>)
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

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		currentLevel.loadLevel(objs);
		player = new Obj_player(300, 300, 50, 80, 0, 0, 0.5);
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

		// Update time
		time++;

		// Update Player position
		player.update();

		// Change level to show all colors ///// NOT LATER IN THE GAME JUST FOR TESTING
		if(time%100==0) {
			if(level!=5)
				level++;
			else
				level=1;
		}
	}

	/*
	Draws all <Obj>'s using the according color template of the level
	 */
	private void paintObjs(Graphics2D g2) {
		// Player
		if(player.is_drawable())
			player.draw(g2, currentLevel.getColor("player"));

		// Walls + Enemies + Texts + Arrows
		for(Obj o : objs) {
			if(o instanceof Obj_wall wall) wall.draw(g2, currentLevel.getColor("wall"));
			//if(o instanceof )
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// Board
		board.draw(g2);

		// <Obj>'s
		paintObjs(g2);

		// Set a font (example)
		g2.setColor(Color.white);
		Font font = new Font ("Courier New", Font.BOLD, 10);
		g2.setFont(font);

		// Pause (needs to be arranged to the center if you change WIDTH or HEIGHT)
		if(isPause) {
			g2.setColor(Color.blue);
			g2.setFont(new Font ("Courier New", Font.BOLD, 50));
			g2.drawString("Game Paused", 230, 310);
		}
	}
}