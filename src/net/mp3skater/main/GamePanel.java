package net.mp3skater.main;

import net.mp3skater.main.elements.Element;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;

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

	// List of Elements
	ArrayList<Element> elements = new ArrayList<>();

	// Booleans for the pause-function
	private boolean exPause = false; // To see if Pause has been changed
	private boolean isPause = false;

	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.getColor("gray", 0x333333));
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

		// Implement KeyListener:
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);

		// Initialize Game
		Utils.setPieces(elements, 5);
	}
	public void launchGame() {
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

		// Insert UPDATE-code here:

		// For example:
		for(Element e : elements) {
			// Move all elements one pixel left-down every frame
			e.setX(e.getX()+1);
			e.setY(e.getY()+1);
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// BOARD
		board.draw(g2);

		// ELEMENTS
		for(Element e : elements) {
			e.draw(g2);
		}

		// SET FONT
		g2.setColor(Color.white);
		Font font = new Font ("Courier New", Font.BOLD, 20);
		g2.setFont(font);

		// PAUSE
		if(isPause) {
			g2.setColor(Color.getColor("purple", 0x8942c8));
			g2.setFont(new Font ("Courier New", Font.BOLD, 100));
			g2.drawString("Game Paused", 70, 425);
		}
	}
}