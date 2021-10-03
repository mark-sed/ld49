package com.sedlacek.ld49.abilities;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class BecomeUnstableItem extends Item {

	
	public BecomeUnstableItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 0;
		this.maxHeal = 0;
		
		this.name = "Angry";
		this.desc = "All become 99 % UNSTABLE";
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(2, 2, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int dmg = c.getMaxUnstable()-c.getUnstable()-1;
			c.setUnstable(c.getUnstable()+dmg);
			new Indicator("+"+dmg+" UNSTABLE", c.getX(), c.getY(), 20,200,60);
		}
	}

}
