package com.sedlacek.ld49.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.abilities.Ability.Target;
import com.sedlacek.ld49.graphics.Animation;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Entity;

public abstract class Character extends Entity {
	
	private Random r = new Random();
	
	public static int speed = 4;
	
	protected int maxUnstable;
	protected int unstable;
	protected int coolDownAmount;
	
	protected Animation unstableIdleAnim,
						unstableAttackAnim,
						unstableWalkingAnim;
	
	protected boolean moving;
	protected boolean coolingDown;
	
	protected Animation moveAnim;
	
	protected ArrayList<Ability> stableAbilities, unstableAbilities;
	
	
	protected void updatePosition(int position) {
		this.x = (Player.CHAR_AMOUNT - position) * widestAnim * Config.SIZE_MULT + (Player.CHAR_AMOUNT - position);
		this.y = Config.HEIGHT - 29*4 - 16 * Config.SIZE_MULT;
	}
	
	public void nextTurnTrigger() {
		if(coolingDown) {
			unstable -= coolDownAmount;
			if(unstable < 0) {
				unstable = 0;
			}
		}
		else {
			gainUnstable(0.1f, 1, 3);
		}
	}
	
	private void gainUnstable(float chance, int min, int max) {
		if(!coolingDown && r.nextFloat() <= chance) {
			int am = r.nextInt(max-min)+min;
			this.unstable+=am;
			new Indicator("+"+am, x, y, 220,220,220);
		}
	}
	
	public void nextLevelTriggered() {
		this.gainUnstable(0.35f, 2, 7);
	}
	
	@Override
	public boolean canBeTargeted(Ability a) {
		if(HP <= 0)
			return false;
		if(a.getTarget() == Target.ENEMY)
			return false;
		return true;
	}
	
	public void move() {
		this.moving = true;
		this.x += speed;
	}
	
	public void update() {
		super.update();
		if(unstable >= maxUnstable) {
			unstable = maxUnstable-1;
			coolingDown = true;
		}
		if(coolingDown && unstable == 0) {
			coolingDown = false;
		}
		if(this.isUnstable()) {
			this.abilities = this.unstableAbilities;
		}
		else
			this.abilities = this.stableAbilities;
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(HP <= 0)
			return;

		float unstableMaxWidth = 50;
		float unstableWidth = unstableMaxWidth / maxUnstable * unstable;
		
		// unstable
		//g.setColor(new Color(250, 250, 250));
		//g.fillRect(x-20+hpBarOffset, y-20-hpBarOffsetY+15, (int)unstableMaxWidth, 10);
		if(coolingDown)
			g.setColor(new Color(224, 153, 40));
		else
			g.setColor(new Color(133, 242, 240));
		g.fillRect(x-20+hpBarOffset, y-20-hpBarOffsetY+15, (int)unstableWidth, 10);
		g.setColor(Color.gray);
		g.drawRect(x-20+hpBarOffset, y-20-hpBarOffsetY+15, (int)unstableMaxWidth, 10);
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getMaxUnstable() {
		return maxUnstable;
	}

	public void setMaxUnstable(int maxUnstable) {
		this.maxUnstable = maxUnstable;
	}

	public int getUnstable() {
		return unstable;
	}

	public void setUnstable(int unstable) {
		this.unstable = unstable;
	}
	
	public boolean isUnstable() {
		return unstable >= maxUnstable || coolingDown;
	}
	
	@Override
	public void skippedTurn() {
		if(!coolingDown) {
			int unst = r.nextInt(4);
			this.unstable += unst;
			if(unst > 0)
				new Indicator("+"+unst+" UNSTABLE", x, y, 25,25,25);
		}
	}
	
	@Override
	public void setHP(int HP) {
		int extraHP = 0;
		if(HP < this.HP && HP <= maxHP*0.15 || HP <= 4) {
			this.gainUnstable(0.90f, 15, 20);
			if(this.isUnstable()) {
				extraHP = 5;
			}
		}
		else if(HP < this.HP) {
			this.gainUnstable(0.25f, 1, 4);
		}
		this.HP = HP+extraHP;
	}

	public int getCoolDownAmount() {
		return coolDownAmount;
	}

	public void setCoolDownAmount(int coolDownAmount) {
		this.coolDownAmount = coolDownAmount;
	}

	public boolean isCoolingDown() {
		return coolingDown;
	}

	public void setCoolingDown(boolean coolingDown) {
		this.coolingDown = coolingDown;
	}

	public ArrayList<Ability> getStableAbilities() {
		return stableAbilities;
	}

	public void setStableAbilities(ArrayList<Ability> stableAbilities) {
		this.stableAbilities = stableAbilities;
	}

	public ArrayList<Ability> getUnstableAbilities() {
		return unstableAbilities;
	}

	public void setUnstableAbilities(ArrayList<Ability> unstableAbilities) {
		this.unstableAbilities = unstableAbilities;
	}
	
}
