package com.sedlacek.ld49.abilities;

import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Entity;

public class SkipTurnAbility extends Ability {
	
	public SkipTurnAbility(int minDmg, int maxDmg, int level, Entity owner, boolean enemy) {
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.level = level;
		
		this.owner = owner;
		
		this.name = "Skip turn";
		this.skip = true;
		
		this.enemy = enemy;
		this.target = Target.ENEMY;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(3, 2, 16, 16, 16);
		
		this.button = new AbilityButton(this);
		if(enemy) {
			this.button.setDisableClick(true);
		}
	}
}
