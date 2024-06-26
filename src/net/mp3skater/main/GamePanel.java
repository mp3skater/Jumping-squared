package net.mp3skater.main;

import net.mp3skater.main.io.Sound;
import net.mp3skater.main.obj.*;
import net.mp3skater.main.io.Board;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.io.Mouse;
import net.mp3skater.main.utils.Draw_Utils;
import net.mp3skater.main.utils.Level_Utils;
import net.mp3skater.main.utils.Menu_Utils;
import net.mp3skater.main.utils.Sound_Utils;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static net.mp3skater.main.utils.Draw_Utils.*;
import static net.mp3skater.main.utils.Misc_Utils.gameWon;
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
	public static int currentMusic = -1;

	// Font Styles
	Font maruMonica;

	// Game State Screens
	public static boolean pauseState, titleState, deathState, controlState, winState, highscoreState;
	public static int titleNum =0,pauseNum =0;

	// Booleans for the pause-function
	public static boolean exPause = true; // To see if Pause has been changed
	private static boolean activatePause = false;

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
	public static Level currentLevel;
	public static int newGame = 2;

	// Time (in frames, 60 = 1 sec)
	public static int time = 0;
	public static int timeTemp;

	// Offset (moves horizontally with the player)
	public static double offset = 0;

	//HEARTS
	public static int leben = 3;

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

		aimPlatform.setImportance(true);
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
	Loads the next level, incrementing <level>
	If it was the last level, the game is over
	 */
	public static void loadLevel(int level) {
		if(level == 5)
			gameWon();
		else {
			clearPlatforms();
			delay = -50;
			offset = 0;
			pauseNum=0;
			GamePanel.level = level;
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
	Only plays the "game over"-se if <se> = true
	 */
	public static void gameOver(boolean dead) {

		if(dead) leben--;
		if(leben==0) {
			time = -1; // It updates the time once, so this sets it to 0 essentially
			if(!winState) deathState = true;
			if(dead) playSE(5);
			stopMusic();
			level = 1;
			leben = 3;
		}
		else if(!winState) playSE(12);
		currentMusic = -1;
		loadLevel(level);

	}

	/*
	Changes the current pause state
	 */
	private void changePauseState() {
		pauseState = !pauseState;
	}

	/*
	Spawn new Platforms and update the pos of <aimPlatform>
	 */
	private void checkPlatforms() {
		// Update the preview platform
		aimPlatform.setPos((int)(mouse.x-(aimPlatform.getSX()/2)+offset), (int)(mouse.y-(aimPlatform.getSY()/2)));

		// Clear platforms
		if(newGame>0) clearPlatforms();

		// New platform
		if(mouse.pressed && !exMClicked && delay+20<time && !player.collides(aimPlatform)) {
			platforms[1] = platforms[0];
			platforms[0] = aimPlatform.neu();
			delay = time;
		}
		exMClicked = mouse.pressed;
	}

	public static Obj_player getPlayer() {
		return (player);
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
		//		", level = "+level+", vec: "+player.getVX()+", "+player.getVY());

		// Devtool
		// Move when paused
		if(KeyHandler.zeroPressed) gameWon();
		//if(GamePanel.pauseState && newGame == 0) {
		//	if(KeyHandler.aPressed || KeyHandler.leftPressed) player.addX(-10); focusCam();
		//	if(KeyHandler.dPressed || KeyHandler.rightPressed) player.addX(10); focusCam();
		//	if(KeyHandler.upPressed) player.addY(-10); focusCam();
		//	if(KeyHandler.downPressed) player.addY(10); focusCam();
		//}

		// Avoid starting with pause menu
		if(newGame>0) pauseState = false;

		// Menu updates
		Menu_Utils.update();

		// Pause, when pause is being pressed
		if(KeyHandler.escPressed && !exPause) changePauseState();
		exPause = KeyHandler.escPressed;

		// Don't continue if Game paused
		if(pauseState || titleState || deathState || winState || controlState || highscoreState) return;

		// Activate pause
		if(activatePause) { changePauseState(); activatePause = false; }

		// Change newGame, other updates
		if(newGame > 0) newGame--;
		time++;

		checkImportance();

		// Update Player position
		player.update();

		// Update the set
		checkPlatforms();

		// Update the Enemy AI
		for(Obj_enemy e : enemies) e.update();
	}

	private void checkImportance() {
		for(Obj e : enemies)
			e.setImportance(e.is_drawable());
		for(Obj w : walls)
			w.setImportance(w.is_drawable());
		for(Obj t : texts)
			t.setImportance(t.is_drawable());
		for(Obj p : platforms)
			if(p!=null) p.setImportance(p.is_drawable());
		for(Obj a : arrows)
			a.setImportance(a.is_drawable());
	}

	/*
	Draws all <Obj>'s using the according color template of the level
	In a specific order, DO NOT CHANGE!
	 */
	private void paintObjs(Graphics2D g2) {
		if(currentLevel == null)
			return;

		// Arrows and texts
		for(Obj_arrow a : arrows) if(a.isImportant()) a.draw(g2, currentLevel.getColor("arrow"));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		for(Obj_text t : texts) if(t.isImportant()) t.draw(g2, currentLevel.getColor("text"));

		// Walls and Endbar
		for(Obj o : walls) {
			if (o instanceof Obj_wall w && o.isImportant()) w.draw(g2, currentLevel.getColor("wall"));
			if (o instanceof Obj_endBar bar && o.isImportant()) bar.draw(g2, currentLevel.getColor("endbar"));
		}

		// Platforms
		for(Obj_platform p : platforms)
			if(p != null && p.isImportant()) p.draw(g2, currentLevel.getColor("platform"));
		if(!pauseState) {
			g2.setStroke(new BasicStroke(5));
			if(aimPlatform != null && !player.collides(aimPlatform))
				aimPlatform.drawAim(g2, currentLevel.getColor("platform"));
		}

		// Enemies
		for(Obj_enemy e : enemies)
			if(e.isImportant()) e.draw(g2, currentLevel.getColor("enemy"));

		// Player
		if(player != null && player.is_drawable())
			player.draw(g2, currentLevel.getColor("player"));
	}

	/*
	Draws the background using the <board>-class
	 */
	private void drawBoard(Graphics2D g2) {
		if(currentLevel != null) board.draw(g2, currentLevel);
    }

	/*
	Gets activated with <repaint();>
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		// Set the font
		g2.setColor(Color.white);
		g2.setFont(maruMonica);

		if(titleState || deathState || winState || highscoreState || currentLevel==null) {
			if(titleState) {
				try {
					drawTitleScreen(g2);
				} catch (IOException | Draw_Utils.BufferedImageGetException e) {
					throw new RuntimeException(e);
				}
			}
			else if(deathState) drawDeathScreen(g2);
			else if(winState) drawWinScreen(g2);
			else drawHighscores(g2);
			return;
		}
		
		// Board
		drawBoard(g2);
		// <Obj>'s
		paintObjs(g2);
		// Time
		g2.setColor(currentLevel.getColor("text"));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,20f));
		g2.drawString("Time: "+time, 5, 20);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));

        try {
            drawHearts(g2);
        } catch (Draw_Utils.BufferedImageGetException e) {
            throw new RuntimeException(e);
        }

		// Draw pause
		if(pauseState) drawPauseScreen(g2);
		else if(controlState) drawOptionControl(g2);
	}
	private void drawHearts(Graphics2D g2) throws Draw_Utils.BufferedImageGetException {
		String[] heartImages = {"/images/heart_blank.png", "/images/heart_blank.png", "/images/heart_blank.png"};

		for (int i = 0; i < leben; i++) {
			heartImages[i] = "/images/heart_full.png";
		}

		int xPosition = 645;
		for (String heartImage : heartImages) {
			Draw_Utils.drawImage(g2, heartImage, new double[]{xPosition, 15}, new int[]{45, 45});
			xPosition += 45;
		}
	}

}