package com.sedlacek.ld49.player;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sedlacek.ld49.abilities.HealAllItem;
import com.sedlacek.ld49.abilities.Item;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.Game.LevelState;
import com.sedlacek.ld49.main.Game.State;
import com.sedlacek.ld49.main.KeyManager;

public class Player {

	public Character[] characters;
	public static int CHAR_AMOUNT = 3;
	private boolean walkIn = false;
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public Player() {
		this.characters = new Character[CHAR_AMOUNT];
		this.characters[0] = new Werewolf(0);
		this.characters[1] = new Joker(1);
		this.characters[2] = new PlagueDoctor(2);
		
		items.add(new HealAllItem());
	}
	
	public void moveAll(int x, int y) {
		for(Character c: characters) {
			c.setX(c.getX() + x);
			c.setY(c.getY() + y);
		}
	}
	
	public void checkDeads() {
		int deads = 0;
		for(int i = 0; i < CHAR_AMOUNT; ++i) {
			if(characters[i].getHP() <= 0) {
				deads++;
			}
		}
		if(deads > 0) {
			if(deads >= characters.length) {
				Game.state = State.GAME_OVER;
				return;
			}
			Character[] newCs = new Character[characters.length-deads];
			int i = 0;
			for(Character c: characters) {
				if(c.getHP() > 0) {
					newCs[i++] = c;
				}
			}
			characters = newCs;
			CHAR_AMOUNT--;
		}
	}
	
	public void resetPositions() {
		walkIn = true;
	}
	
	public void input() { 
		if(Game.levelState == LevelState.EXPLORING && !walkIn) {
			if(Game.getKeyManager().keys[KeyManager.D]) {
				for(Character c: characters) {
					c.move();
				}
			} else {
				for(Character c: characters) {
					c.setMoving(false);
				}
			}
		}
	}
	
	public void update() {
		for(Character c: characters) {
			c.update();
		}
		if(walkIn) {
			for(Character c: characters) {
				c.setX(c.getX()+Character.speed);
				c.setMoving(true);
			}
			if(characters[CHAR_AMOUNT-1].getX() >= 40) {
				walkIn = false;
				for(Character c: characters) {
					c.setMoving(false);
				}
			}
		}else {
			if(characters[CHAR_AMOUNT-1].getX() >= Config.WIDTH) {
				Game.level.nextLevel();
				for(Character c: characters) {
					c.setX(c.getX()-Config.WIDTH-450);
					c.setMoving(false);
				}
			}
		}
	}
	
	public void fixedUpdate() {
		for(Character c: characters) {
			c.fixedUpdate();
		}
	}
	
	public void render(Graphics g) {
		for(Character c: characters) {
			c.render(g);
		}
	}
}
