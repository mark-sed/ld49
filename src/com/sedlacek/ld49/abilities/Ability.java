package com.sedlacek.ld49.abilities;

import java.awt.image.BufferedImage;

import com.sedlacek.ld49.gui.AbilityButton;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;

public abstract class Ability {

	protected int minDmg;
	protected int maxDmg;
	
	protected int minHeal;
	protected int maxHeal;
	
	protected int level;
	
	protected String name;
	
	protected Entity owner;
	
	protected AbilityButton button;
	
	protected BufferedImage icon;
	
	protected boolean enemy;
	
	protected boolean aoe;
	
	protected boolean skip;
	
	public static enum Target {
		ENEMY,
		PLAYER,
		ALL,
		TEAMMATE
	}
	
	protected Target target;
	
	public void clicked() {
		if(Game.game.level.getCurrentPlayer() != owner) {
			return;
		}
		if(this.skip) {
			owner.useAbility(this);
			Game.level.nextTurn();
			return;
		}
		if(Game.level.selector.getAbility() == this) {
			Game.level.selector.setAbility(null);
		}
		else {
			Game.level.selector.setAbility(this);
		}
	}

	public int getMinDmg() {
		return minDmg;
	}

	public void setMinDmg(int minDmg) {
		this.minDmg = minDmg;
	}

	public int getMaxDmg() {
		return maxDmg;
	}

	public void setMaxDmg(int maxDmg) {
		this.maxDmg = maxDmg;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	public AbilityButton getButton() {
		return button;
	}

	public void setButton(AbilityButton button) {
		this.button = button;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnemy() {
		return enemy;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public boolean isAoe() {
		return aoe;
	}

	public void setAoe(boolean aoe) {
		this.aoe = aoe;
	}
	

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int getMinHeal() {
		return minHeal;
	}

	public void setMinHeal(int minHeal) {
		this.minHeal = minHeal;
	}

	public int getMaxHeal() {
		return maxHeal;
	}

	public void setMaxHeal(int maxHeal) {
		this.maxHeal = maxHeal;
	}
	
}
