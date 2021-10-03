package com.sedlacek.ld49.abilities;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Entity;

public class HealAbility extends Ability {
	
	public HealAbility(int minHeal, int maxHeal, int level, Entity owner, boolean enemy) {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = level;
		this.minHeal = minHeal;
		this.maxHeal = maxHeal;
		
		this.owner = owner;
		
		this.name = "Heal";
		
		this.enemy = enemy;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(3, 1, 16, 16, 16);
		
		this.button = new AbilityButton(this);
		if(enemy) {
			this.button.setDisableClick(true);
		}
	}
}
