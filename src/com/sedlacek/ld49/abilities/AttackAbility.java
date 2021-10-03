package com.sedlacek.ld49.abilities;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Entity;

public class AttackAbility extends Ability {
	
	public AttackAbility(int minDmg, int maxDmg, int level, Entity owner, boolean enemy) {
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.level = level;
		
		this.owner = owner;
		
		this.name = "Attack";
		
		this.enemy = enemy;
		this.target = Target.ENEMY;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(1, 1, 16, 16, 16);
		
		this.button = new AbilityButton(this);
		if(enemy) {
			this.button.setDisableClick(true);
		}
	}
}
