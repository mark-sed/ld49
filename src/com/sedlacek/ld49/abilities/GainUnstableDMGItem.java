package com.sedlacek.ld49.abilities;

import java.util.Random;

import com.sedlacek.ld49.abilities.Ability.Target;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class GainUnstableDMGItem extends Item {

	private Random r = new Random();
	
	public GainUnstableDMGItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 1;
		this.maxHeal = 5;
		
		this.name = "Sharpen claws";
		this.desc = "All gain DMG for UNSTABLE state "+minHeal+"-"+maxHeal;
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(6, 1, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int dmg = r.nextInt((maxHeal+1)-minHeal)+minHeal;
			for(Ability a: c.getUnstableAbilities()) {
				if(a.getMaxDmg() > 0) {
					a.setMinDmg(a.getMinDmg()+dmg);
					a.setMaxDmg(a.getMaxDmg()+dmg);
					new Indicator("+"+dmg+" DMG", c.getX(), c.getY(), 120,20,60);
				}
			}
		}
	}
}
