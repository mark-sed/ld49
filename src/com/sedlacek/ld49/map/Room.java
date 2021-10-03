package com.sedlacek.ld49.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld49.enemies.Enemy;
import com.sedlacek.ld49.enemies.Mushroom;
import com.sedlacek.ld49.enemies.Rat;
import com.sedlacek.ld49.enemies.Slime;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.gui.Shop;
import com.sedlacek.ld49.gui.StatsBar;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.Game.LevelState;
import com.sedlacek.ld49.player.Player;

public class Room {

	private int number;
	private BufferedImage bckg;
	public LevelState startingState = LevelState.EXPLORING;
	public ArrayList<Enemy> enemies;
	public int y = 0;
	private final int yMin = -StatsBar.HEIGHT;
	private int scrollSpeed = 10;
	public boolean initializing = false;
	public boolean uninitializing = false;
	private long lastTime;
	
	public StatsBar[] statsBars;
	public StatsBar selectedStats;
	
	private Random r = new Random();
	
	public Shop shop;
	
	public Room(int number) {
		this.number = number;
		this.bckg = ImageLoader.loadNS("/sewer.png");
		enemies = new ArrayList<Enemy>();
		this.fillRoom(number);
		if(enemies.size() > 0)
			startingState = LevelState.COMBAT;
		else
			startingState = LevelState.EXPLORING;
	}
	
	private void fillRoom(int number) {
		if(number <= 1)
			return;
		else if(number == 40 || number == 35 || number == 30 ||
				number == 20 || number == 15 || number == 5  ||
				number == 10 || number == 20) {
			shop = new Shop(number);
		}
		else if(number == 31) {
			Enemy e = new Slime(0, number);
			e.setMaxHP(e.getMaxHP()*2);
			e.setHP(e.getMaxHP());
			e.getAbilities().get(0).setAoe(true);
			e.getAbilities().get(0).setMinDmg(e.getAbilities().get(0).getMinDmg()+3);
			e.getAbilities().get(0).setMaxDmg(e.getAbilities().get(0).getMaxDmg()+4);
			enemies.add(e);
		}
		else if(number == 41) {
			Enemy e = new Slime(0, number);
			e.setMaxHP(e.getMaxHP()*2);
			e.setHP(e.getMaxHP());
			e.getAbilities().get(0).setAoe(true);
			e.getAbilities().get(0).setMinDmg(e.getAbilities().get(0).getMinDmg()+3);
			e.getAbilities().get(0).setMaxDmg(e.getAbilities().get(0).getMaxDmg()+5);
			enemies.add(e);
			Enemy e2 = new Mushroom(1, number);
			e2.setMaxHP(e2.getMaxHP()*2);
			e2.setHP(e2.getMaxHP());
			e2.getAbilities().get(0).setMinDmg(e2.getAbilities().get(0).getMinDmg()+1);
			e2.getAbilities().get(0).setMaxDmg(e2.getAbilities().get(0).getMaxDmg()+2);
			enemies.add(e2);
		}
		else if(r.nextFloat() < 0.70f) {
			int minEnemies = 1;
			int maxEnemies = 2;
			float ratCh = 0.8f;
			float slimeCh = 0.2f;
			float mushCh = 0.0f;
			if(number > 15) {
				minEnemies = 3;
				maxEnemies = 4;
				mushCh = 0.4f;
				slimeCh = 0.55f;
				ratCh = 0.05f;
			}
			else if(number > 10) {
				minEnemies = 2;
				maxEnemies = 4;
				mushCh = 0.30f;
				slimeCh = 0.55f;
				ratCh = 0.15f;
			}
			else if(number > 5) {
				minEnemies = 1;
				maxEnemies = 3;
				mushCh = 0.10f;
				slimeCh = 0.45f;
				ratCh = 0.45f;
			}
			
			int enemyAm = r.nextInt((maxEnemies+1)-minEnemies)+minEnemies;
			for(int i = 0; i < enemyAm; ++i) {
				float roll = r.nextFloat();
				if(number >= 15) {
					if(roll <= ratCh) {
						enemies.add(new Rat(i, number));
					}
					else if(roll <= mushCh) {
						enemies.add(new Mushroom(i, number));
					}
					else {
						enemies.add(new Slime(i, number));
					}
				}
				else if(number >= 10) {
					if(roll <= ratCh) {
						enemies.add(new Rat(i, number));
					}
					else if(roll <= mushCh) {
						enemies.add(new Mushroom(i, number));
					}
					else {
						enemies.add(new Slime(i, number));
					}
				}
				else if(number >= 5) {
					if(roll <= mushCh) {
						enemies.add(new Mushroom(i, number));
					}
					else if(roll <= ratCh) {
						enemies.add(new Rat(i, number));
					}
					else {
						enemies.add(new Slime(i, number));
					}
				}
				else {
					if(roll <= mushCh) {
						enemies.add(new Mushroom(i, number));
					}
					else if(roll <= slimeCh) {
						enemies.add(new Slime(i, number));
					}
					else {
						enemies.add(new Rat(i, number));
					}
				}
			}
		}
	}
	
	public void init(Level level) {
		initializing = true;
		int statsBarsAmount = enemies.size()+Player.CHAR_AMOUNT;
		statsBars = new StatsBar[statsBarsAmount];
		int i = 0;
		for(; i < Player.CHAR_AMOUNT; ++i) {
			statsBars[i] = new StatsBar(0, y+Config.HEIGHT, Game.game.player.characters[i], false);
			statsBars[i].setP(Game.game.player.characters[i]);
		}
		for(Enemy e: enemies) {
			statsBars[i] = new StatsBar(0, y+Config.HEIGHT, e, true);
			++i;
		}
		
		selectedStats = statsBars[0];
		level.entities = new ArrayList<Entity>();
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			level.entities.add(c);
		}
		for(Enemy e: enemies) {
			level.entities.add(e);
		}
	}
	
	public void update() {
		if(shop != null) {
			shop.update();
		}
		
		boolean isSomeEnemyAlive = false;
		for(Enemy e: enemies) {
			e.update();
			if(e.getHP() > 0) {
				isSomeEnemyAlive = true;
			}
		}
		if(initializing && y > yMin && System.currentTimeMillis() - lastTime >= 50) {
			y -= scrollSpeed;
			for(Enemy e: enemies) {
				e.setY(e.getY() - scrollSpeed);
			}
			for(StatsBar s: statsBars) {
				s.setY(s.getY() - scrollSpeed);
			}
			Game.game.player.moveAll(0, -scrollSpeed);
			lastTime = System.currentTimeMillis();
		}
		else if(!uninitializing && !isSomeEnemyAlive) {
			uninitializing = true;
			initializing = false;
		}
		else if(uninitializing && y < 0 && System.currentTimeMillis() - lastTime >= 50) {
			y += scrollSpeed;
			for(Enemy e: enemies) {
				e.setY(e.getY() + scrollSpeed);
			}
			for(StatsBar s: statsBars) {
				s.setY(s.getY() + scrollSpeed);
			}
			Game.game.player.moveAll(0, scrollSpeed);
			lastTime = System.currentTimeMillis();
			Game.game.levelState = LevelState.EXPLORING;
		}
		
		if(initializing && y <= yMin) {
			initializing = false;
		}
		else if(uninitializing && y == 0) {
			uninitializing = false;
		}
		
		if(Game.game.levelState == LevelState.COMBAT) {
			boolean wasMouseOver = false;
			StatsBar currPlayerStats = null;
			for(StatsBar s: statsBars) {
				if(s.getE() == Game.level.getCurrentPlayer()) {
					currPlayerStats = s;
				}
				if(s.isMouseOver()) {
					selectedStats = s;
					wasMouseOver = true;
					break;
				}
			}
			if(!wasMouseOver) {
				selectedStats = currPlayerStats;
			}
		}
		
		if(selectedStats != null) {
			selectedStats.update();
		}
	}
	
	public void fixedUpdate() {
		for(Enemy e: enemies) {
			e.fixedUpdate();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(bckg, 0, y, Config.WIDTH, Config.HEIGHT, null);
	}
}
