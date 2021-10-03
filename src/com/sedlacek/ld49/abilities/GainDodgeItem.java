package com.sedlacek.ld49.abilities;

import java.util.Random;

import com.sedlacek.ld49.abilities.Ability.Target;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Game;

public class GainDodgeItem  extends Item {

	private Random r = new Random();
	
	public GainDodgeItem() {
		this.minDmg = 0;
		this.maxDmg = 0;
		this.level = 1;
		this.minHeal = 4;
		this.maxHeal = 8;
		
		this.name = "Fast";
		this.desc = "All gain dodge "+minHeal+"-"+maxHeal;
		
		this.oneTimeUse = true;
		
		this.enemy = false;
		this.target = Target.PLAYER;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/icons.png"));
		this.icon = ss.grabImage(4, 2, 16, 16, 16);
		
		this.button = new AbilityButton(this);
	}

	@Override
	public void use() {
		for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
			int dmg1 = r.nextInt((maxHeal+1)-minHeal)+minHeal;
			float dmg = ((float)dmg1)/100;
			System.out.println(dmg+" "+c.getDodgeChance()+dmg);
			c.setDodgeChance(c.getDodgeChance()+dmg);
			if(c.getDodgeChance() >= 0.8f)
				c.setDodgeChance(0.8f);
			new Indicator("+"+dmg1+" DODGE", c.getX(), c.getY(), 20,200,60);
		}
	}

}
