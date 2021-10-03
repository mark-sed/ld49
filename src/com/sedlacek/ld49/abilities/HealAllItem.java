package com.sedlacek.ld49.abilities;

import java.util.Random;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class HealAllItem extends Item {

	private Random r = new Random();
	
	public HealAllItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 15;
		this.maxHeal = 30;
		
		this.name = "Heal all";
		this.desc = "Heals for "+minHeal+"-"+maxHeal;
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(4, 1, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int heal = r.nextInt((maxHeal+1)-minHeal)+minHeal;
			if(c.getHP() + heal > c.getMaxHP()) {
				c.setHP(c.getMaxHP());
			}
			else {
				c.setHP(c.getHP() + heal);
			}
			new Indicator("+"+heal, c.getX(), c.getY(), 0,200,0);
		}
	}
}
