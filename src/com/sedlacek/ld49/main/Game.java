package com.sedlacek.ld49.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JFrame;

import com.sedlacek.ld49.gui.GUIHandler;
import com.sedlacek.ld49.map.Level;
import com.sedlacek.ld49.player.Player;

/*
 * @Author: Marek Sedlacek
 * mr.mareksedlacek@gmail.com
 * Twitter: @Sedlacek
 * 
 */

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static Rectangle windowRect;
	
	private static int infoFPS;
	private static int infoTicks;
	
	public static boolean soundOn = true;
	public static boolean musicOn = true;
	
	public static Random r = new Random();
	
	public static Game game; 
	public static JFrame frame;
	public static Thread thread;
	
	public static enum State{
		GAME,
		GAME_OVER,
		MENU,
		GAME_WON
	}
	
	public static enum LevelState {
		EXPLORING,
		COMBAT,
		SHOPPING,
		TRANSITION
	}
	
	public static enum Turn {
		PLAYER,
		ENEMY
	}
	
	public static State state;
	public static LevelState levelState;
	public static Turn turn;
	
	private BufferStrategy bs;
	private Graphics g;
	private static KeyManager keyManager;
	private static MouseManager mouseManager;
	private long lastTime;

	public static boolean paused = false;

	private static boolean mute = false;
	public static Level level;
	public Player player;

	public Game(){
		keyManager = new KeyManager();
		this.addKeyListener(keyManager);
		mouseManager = new MouseManager();
		this.addMouseListener(mouseManager);
		this.addMouseMotionListener(mouseManager);
		this.addMouseWheelListener(mouseManager);
		
		//Sheet = ImageLoader.loadNS("/furniture.png");
		
		//FONTS
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont01.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont02.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			InputStream font = getClass().getResourceAsStream("/DorFont03.ttf");
			 GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font)); 
		}catch(Exception e){
			e.printStackTrace();
		}

		state = State.GAME;
		levelState = LevelState.EXPLORING;
		player = new Player();

		//audioGalery = new AudioGalery();
		//AudioGalery.music.loop();
		level = new Level();
	}
	
	private void update(){
		if(state == State.GAME){
			level.update();
			player.update();
			GUIHandler.update();
		}else if(state == State.MENU){
			
		}else if(state == State.GAME_OVER){
			//
		}
		
		if(System.currentTimeMillis() - lastTime >= 1000){
			this.lastTime = System.currentTimeMillis();
			this.fixedUpdate();
		}
		if(mouseManager.LClicked){
			mouseManager.LClicked = false;
		}
	}

	private void fixedUpdate(){
		// Invoked every second
		if(state == State.GAME){
			level.fixedUpdate();
			player.fixedUpdate();
		}
	}

	public static void mute(){
		if(mute){
			//AudioGalery.music.loop();
			Config.debug("Start Music");
			musicOn = true;
		}else{
			//AudioGalery.music.stopClip();
			Config.debug("Stop Music");
			musicOn = false;
		}
		mute = !mute;
	}
	
	private void input(){
		if(getKeyManager().keys[KeyManager.F1]){
			if(Config.showInfo) {
				Config.showInfo = false;
			}else {
				Config.showInfo = true;
			}
			getKeyManager().keys[KeyManager.F1] = false;
		}
		if(getKeyManager().keys[KeyManager.S]){
			mute();
			getKeyManager().keys[KeyManager.S] = false;
		}
		if(state == State.GAME){
			player.input();
		}
	}
	
	private void render(){
		bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		
		g = bs.getDrawGraphics();
		//Draw
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

		if(state == State.GAME || state == State.GAME_OVER){
			level.render(g);
			player.render(g);
			GUIHandler.render(g);
		}
		else if(state == State.MENU){
			
		}
		if(state == State.GAME_OVER){
			g.setColor(Color.black);
			g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("DorFont03", Font.BOLD, 48));
			g.drawString("Game over", Config.WIDTH/2-g.getFontMetrics().stringWidth("Game over")/2, Config.HEIGHT/2-30);
		}
		if(state == State.GAME_WON){
			g.setColor(Color.black);
			g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("DorFont03", Font.BOLD, 48));
			g.drawString("You did it! You escaped!", Config.WIDTH/2-g.getFontMetrics().stringWidth("You did it! You escaped!")/2, Config.HEIGHT/2-30);
		}
		
		if(Config.showInfo){
			g.setFont(new Font("Consolas", Font.PLAIN, 11));
			g.setColor(Color.WHITE);
			g.drawString("FPS/Ticks: " + infoFPS + "/" + infoTicks, Config.WIDTH-100, 12);
		}
		//Dispose
		bs.show();
		g.dispose();
	}
	
	public static KeyManager getKeyManager(){
		return keyManager;
	}
	
	public static MouseManager getMouseManager(){
		return mouseManager;
	}
	
	public static Rectangle getMouseRect(){
		return mouseManager.getMouseRect();
	}
	
	public void run(){
		this.requestFocus();
		double ns = 1000000000 / Config.fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frames = 0;
		
		while(Config.running){
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				input();
				update();
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(timer >= 1000000000){
				//System.out.println(ticks + "   " + frames);
				infoTicks = ticks;
				infoFPS = frames;
				ticks = 0;
				frames = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	
	
	public static void main(String [] args){
		if(args.length > 0){
			if(args[0].equals("-d") || args[0].equals("--debug")){
				Config.debugMode = true;
				Config.showInfo = true;
				Config.debug("Debug mode on");
			}
		}

		game = new Game();
		
		frame = new JFrame(Config.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Config.WIDTH=screenSize.Config.WIDTH;
		Config.HEIGHT=screenSize.Config.HEIGHT;
	    frame.setBounds(0,0,screenSize.Config.WIDTH, screenSize.Config.HEIGHT);
	    frame.setUndecorated(true);*/
	    frame.setBounds(0,0, Config.WIDTH, Config.HEIGHT);
	    windowRect = new Rectangle(0, 0, Config.WIDTH, Config.HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	    
		frame.add(game);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		game.start();
	}
	
	public void start(){
		if(Config.running)
			return;
		Config.running = true;
		thread = new Thread(this, "ld49");
		thread.start();
	}
	
	public void stop(){
		if(!Config.running)
			return;
		Config.running = false;
	}
}
