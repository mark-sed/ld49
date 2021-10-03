package com.sedlacek.ld49.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.main.CollisionBox;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;

public class StatsBar {

	public static final int HEIGHT = 250;
	public static final int WIDTH = Config.WIDTH;
	
	private int x, y;
	private Entity e;
	private com.sedlacek.ld49.player.Character p;
	private boolean enemy;
	
	public StatsBar(int x, int y, Entity e, boolean enemy) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.enemy = enemy;
	}
	
	public void update() {
		for(Ability a: e.getAbilities()) {
			if(a.getButton() != null) {
				a.getButton().update();
			}
		}
	}
	
	public boolean isMouseOver() {
		if(e.getCollisionBox().isCollision(new CollisionBox(Game.getMouseManager().MX-2, Game.getMouseManager().MY-2, 4, 4))) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		// Background
		if(enemy) {
			g.setColor(new Color(71, 45, 45));
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(new Color(71, 45, 45).brighter());
			g.fillRect(x, y, WIDTH, 10);
			g.fillRect(x, y+HEIGHT-40, WIDTH, 40);
			g.fillRect(x, y, 10, HEIGHT);
			g.fillRect(x+WIDTH-10, y, 10, HEIGHT);
		} else {
			g.setColor(new Color(60, 39, 79));
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(new Color(60, 39, 79).brighter());
			g.fillRect(x, y, WIDTH, 10);
			g.fillRect(x, y+HEIGHT-40, WIDTH, 40);
			g.fillRect(x, y, 10, HEIGHT);
			g.fillRect(x+WIDTH-10, y, 10, HEIGHT);
		}
		
		
		// Icon
		g.setColor(Color.lightGray);
		g.fillRect(x+15, y+15, e.getIcon().getWidth()*Config.SIZE_MULT+10, e.getIcon().getWidth()*Config.SIZE_MULT+10);
		g.drawImage(e.getIcon(), x+20, y+20, e.getIcon().getWidth()*Config.SIZE_MULT, e.getIcon().getHeight()*Config.SIZE_MULT, null);
		
		// Print stats
		g.setColor(Color.white);
		g.setFont(new Font("DorFont01", Font.PLAIN, 40));
		g.drawString(e.getName(), x+e.getIcon().getWidth()*Config.SIZE_MULT+30, y+35);
		g.setColor(Color.lightGray);
		g.setFont(new Font("DorFont01", Font.PLAIN, 32));
		g.drawString(e.getType(), x+e.getIcon().getWidth()*Config.SIZE_MULT+30, y+35+25);
		
		int offst = 100;
		
		int statsStart = y+e.getIcon().getHeight()*Config.SIZE_MULT+20+10+20;
		g.setColor(Color.white);
		g.setFont(new Font("DorFont01", Font.PLAIN, 32));
		g.drawString("HP: ", x+15, statsStart);
		g.drawString(e.getHP()+"/"+e.getMaxHP(), x+15+offst, statsStart);
		
		statsStart += 20;
		
		if(!enemy) {
			g.drawString("UNSTBL: ", x+15, statsStart);
			g.drawString(p.getUnstable()+"/"+p.getMaxUnstable(), x+15+offst, statsStart);
			statsStart += 20;
		}
		
		g.drawString("DODGE: ", x+15, statsStart);
		g.drawString((int)(e.getDodgeChance()*100) +" %", x+15+offst, statsStart);
		statsStart += 20;
		
		// Render abilities
		int xPos = x+Config.WIDTH/2;
		int yPos = y + 15;
		Ability selectedAb = null;
		for(Ability a: e.getAbilities()) {
			g.setColor(Color.lightGray);
			g.fillRect(xPos, yPos, 64, 64);
			if(a.getButton() != null) {
				a.getButton().render(g, xPos, yPos, 64, 64);
			}
			xPos += 64 + 10;
			if(a.getButton().mouseOver) {
				selectedAb = a;
			}
		}
		
		xPos = x+Config.WIDTH/2;
		yPos += 64+10+20;
		if(selectedAb != null) {
			g.setColor(Color.white);
			g.setFont(new Font("DorFont01", Font.PLAIN, 40));
			g.drawString(selectedAb.getName(), xPos, yPos);
			yPos += 15;
			g.setFont(new Font("DorFont01", Font.PLAIN, 24));
			g.setColor(Color.lightGray);
			g.drawString("Level "+selectedAb.getLevel(), xPos, yPos);
			yPos += 25;
			g.setFont(new Font("DorFont01", Font.PLAIN, 32));
			g.setColor(Color.white);
			if(selectedAb.getMaxDmg() > 0) {
				g.drawString("DMG: "+selectedAb.getMinDmg()+"-"+selectedAb.getMaxDmg(), xPos, yPos);
			}
			else if(selectedAb.getMaxHeal() > 0){
				g.drawString("HEAL: "+selectedAb.getMinHeal()+"-"+selectedAb.getMaxHeal(), xPos, yPos);
			}
			yPos += 20;
			if(selectedAb.isAoe()) {
				g.drawString("AOE", xPos, yPos);
				yPos += 20;
			}
		}
		
		if(Game.level.selector.getAbility() != null) {
			g.drawImage(Game.level.selector.getAbility().getIcon(), 10, y-74, 64, 64, null);
		}
		
		if(Game.game.level.getCurrentPlayer() != e) {
			g.setColor(new Color(0,0,0,60));
			g.fillRect(x, y, WIDTH, HEIGHT);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public com.sedlacek.ld49.player.Character getP() {
		return p;
	}

	public void setP(com.sedlacek.ld49.player.Character p) {
		this.p = p;
	}

	public Entity getE() {
		return e;
	}

	public void setE(Entity e) {
		this.e = e;
	}
	
}
