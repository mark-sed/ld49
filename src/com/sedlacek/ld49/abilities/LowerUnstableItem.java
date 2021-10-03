package com.sedlacek.ld49.abilities;

import java.util.Random;

import com.sedlacek.ld49.abilities.Ability.Target;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class LowerUnstableItem extends Item {

	private Random r = new Random();
	
	public LowerUnstableItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 5;
		this.maxHeal = 8;
		
		this.name = "Craziness";
		this.desc = "Lower UNSTABLE threshold "+minHeal+"-"+maxHeal;
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(1, 2, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int dmg = r.nextInt((maxHeal+1)-minHeal)+minHeal;
			c.setMaxUnstable(c.getMaxUnstable()-dmg);
			if(c.getMaxUnstable() <= 0)
				c.setMaxUnstable(1);
			c.setUnstable(c.getUnstable()-dmg);
			if(c.getUnstable() <= 0) {
				c.setUnstable(0);
			}
			new Indicator("+"+dmg+" UNSTABLE", c.getX(), c.getY(), 20,200,60);
		}
	}
}
