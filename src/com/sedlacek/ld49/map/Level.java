package com.sedlacek.ld49.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.sedlacek.ld49.enemies.Enemy;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.gui.Selector;
import com.sedlacek.ld49.main.CollisionBox;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.Game.LevelState;
import com.sedlacek.ld49.main.Game.State;
import com.sedlacek.ld49.main.Game.Turn;

public class Level {

	private static int LEVEL_SIZE = 42;
	
	public Room[] rooms;
	public int currentRoom = 0;
	private int darkness = 100;
	private int combatDarkness = 100;
	
	private long lastTime;
	private int transitionDarkness;
	private boolean fadeToBlack;
	
	public Selector selector;
	public Selector currentPlayerSelector;
	
	public ArrayList<Entity> entities;
	public int currentPlayer;
	
	public int lastAttacker;
	public long lastAttackTime;
	public boolean turnEnded;
	
	public Level() {
		this.rooms = new Room[LEVEL_SIZE];
		for(int i = 0; i < LEVEL_SIZE; ++i) {
			this.rooms[i] = new Room(i+1);
		}
		selector = new Selector();
		currentPlayerSelector = new Selector();
		currentPlayerSelector.setColor(new Color(250, 255, 107, 20));
	}
	
	public void nextLevel() {
		if(currentRoom >= LEVEL_SIZE || Game.levelState == LevelState.TRANSITION)
			return;
		Game.levelState = LevelState.TRANSITION;
		transitionDarkness = darkness;
		fadeToBlack = true;
		// Check for dead players
		Game.game.player.checkDeads();
		Game.game.player.resetPositions();
		Game.turn = Turn.PLAYER;
	}
	
	public void nextTurn() {
		currentPlayer++;
		if(currentPlayer >= entities.size())
			currentPlayer = 0;
		while(getCurrentPlayer().getHP() <= 0) {
			++currentPlayer;
			if(currentPlayer >= entities.size())
				currentPlayer = 0;
		}
		currentPlayerSelector.setO(getCurrentPlayer());
		for(Entity e: rooms[currentRoom].enemies) {
			e.nextTurnTrigger();
		}
		for(Entity e: Game.game.player.characters) {
			e.nextTurnTrigger();
		}
	}
	
	private void transitionEnd() {
		currentRoom++;
		if(currentRoom >= LEVEL_SIZE-1) {
			Game.state = State.GAME_WON;
			return;
		}
		new Indicator("Room: "+(currentRoom+1)+"/"+LEVEL_SIZE, Config.WIDTH/2-160, Config.HEIGHT/2-80, 255,255,255, 0, new Font("DorFont03", Font.BOLD, 48));
		Game.levelState = rooms[currentRoom].startingState;
		rooms[currentRoom].init(this);
		currentPlayer = 0;
		currentPlayerSelector.setO(entities.get(currentPlayer));
		for(Entity e: rooms[currentRoom].enemies) {
			e.nextLevelTriggered();
		}
		for(Entity e: Game.game.player.characters) {
			e.nextLevelTriggered();
		}
	}
	
	public ArrayList<Enemy> getEnemies() {
		 return this.rooms[currentRoom].enemies;
	}
	
	public void update() {
		if(Game.levelState == LevelState.TRANSITION) {
			// Play transition
			if(System.currentTimeMillis() - lastTime >= 100) {
				if(fadeToBlack)
					transitionDarkness += 10;
				else
					transitionDarkness -= 10;
				lastTime = System.currentTimeMillis();
			}
			if(transitionDarkness >= 220) {
				fadeToBlack = false;
			}
			if(rooms[currentRoom].startingState != LevelState.COMBAT) {
				if(!fadeToBlack && transitionDarkness <= darkness) {
					transitionEnd();
				}
			}
			else {
				if(!fadeToBlack && transitionDarkness <= combatDarkness) {
					transitionEnd();
				}
			}
		}
		else {
			if(Game.levelState == LevelState.COMBAT) {
				currentPlayerSelector.update();
				if(this.getCurrentPlayer().isEnemy() && !turnEnded) {
					lastAttacker = currentPlayer;
					this.getCurrentPlayer().attack(Game.game.player.characters);
				}
				
				if(turnEnded && System.currentTimeMillis() - this.lastAttackTime >= 900) {
					this.nextTurn();
					turnEnded = false;
					return;
				}
			}
			selector.update();
			rooms[currentRoom].update();
			if(rooms[currentRoom].selectedStats != null) {
				rooms[currentRoom].selectedStats.update();
			}
			selector.setO(null);
			for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
				if(c.getCollisionBox() != null && c.getCollisionBox().isCollision(new CollisionBox(Game.getMouseManager().MX-2, Game.getMouseManager().MY-2, 4, 4))){
					selector.setO(c);
					break;
				}
			}
			if(selector.getO() == null) {
				for(Enemy e: rooms[currentRoom].enemies) {
					if(e.getCollisionBox() != null && e.getCollisionBox().isCollision(new CollisionBox(Game.getMouseManager().MX-2, Game.getMouseManager().MY-2, 4, 4))){
						selector.setO(e);
						break;
					}
				}
			}
		}
	}
	
	public Entity getCurrentPlayer() {
		if(Game.levelState != LevelState.COMBAT)
			return null;
		return entities.get(currentPlayer);
	}
	
	public void fixedUpdate() {
		if(Game.levelState != LevelState.TRANSITION) {
			rooms[currentRoom].fixedUpdate();
		}
	}
	
	
	public void render(Graphics g) {
		rooms[currentRoom].render(g);
		if(Game.levelState == LevelState.TRANSITION) {
			g.setColor(new Color(0,0,0,transitionDarkness));
		}else if(Game.levelState == LevelState.COMBAT) {
			g.setColor(new Color(0,0,0,combatDarkness));
		}else {
			g.setColor(new Color(0,0,0,darkness));
		}
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
		
		for(Enemy e: rooms[currentRoom].enemies) {
			e.render(g);
		}
		if(rooms[currentRoom].selectedStats != null) {
			rooms[currentRoom].selectedStats.render(g);
		}
		if(Game.levelState == LevelState.COMBAT) {
			currentPlayerSelector.render(g);
		}
		
		selector.render(g);
		
		if(rooms[currentRoom].shop != null) {
			rooms[currentRoom].shop.render(g);
		}
	}
}
