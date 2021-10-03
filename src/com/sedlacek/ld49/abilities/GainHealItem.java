package com.sedlacek.ld49.abilities;

import java.util.Random;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class GainHealItem extends Item {

	private Random r = new Random();
	
	public GainHealItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 2;
		this.maxHeal = 4;
		
		this.name = "Bandages";
		this.desc = "Better heals when in stable state "+minHeal+"-"+maxHeal;
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(7, 1, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int dmg = r.nextInt((maxHeal+1)-minHeal)+minHeal;
			for(Ability a: c.getStableAbilities()) {
				if(a.getMaxHeal() > 0) {
					a.setMinHeal(a.getMinHeal()+dmg);
					a.setMaxHeal(a.getMaxHeal()+dmg);
					new Indicator("+"+dmg+" HEAL", c.getX(), c.getY(), 20,200,60);
				}
			}
		}
	}

}
