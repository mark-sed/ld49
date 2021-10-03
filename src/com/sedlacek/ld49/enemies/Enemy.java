package com.sedlacek.ld49.enemies;

import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.abilities.Ability.Target;
import com.sedlacek.ld49.graphics.Indicator;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;

public abstract class Enemy extends Entity {

	private Random r = new Random();
	protected int room;
	
	protected void updatePosition(int position) {
		this.x = Config.WIDTH/2 + position * idleAnim.getWidth() * Config.SIZE_MULT + position * 20;
		this.y = Config.HEIGHT - 29*4 - 16 * Config.SIZE_MULT;
	}
	
	@Override
	public boolean canBeTargeted(Ability a) {
		if(HP <= 0)
			return false;
		return !a.isEnemy() && (a.getTarget() == Target.ENEMY || a.getTarget() == Target.ALL);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}
	
	public void update() {
		super.update();
		if(this.currAnim == this.attackAnim) {
			if(this.currAnim.isDone()) {
				this.currAnim = this.idleCombatAnim;
				this.attackAnim.reset();
				this.attacking = false;
			}
		}
		if(this.attacking) {
			this.currAnim = this.attackAnim;
		}
		else {
			this.currAnim = this.idleCombatAnim;
		}
	}
	
	@Override
	public void attack(Entity ... enemies) {
		if(!this.abilities.get(0).isAoe()) {
			Entity picked;
			if(r.nextBoolean()) {
				// Find lowest hp
				Entity lowestE = null;
				int lowestEValue = 0;
				for(Entity e: enemies) {
					if(lowestE == null || e.getHP() < lowestEValue && e.getHP() > 0) {
						lowestE = e;
						lowestEValue = e.getHP();
					}
				}
				picked = lowestE;
			}else {
				int i = 0;
				do {
					i = r.nextInt(enemies.length);
				} while(enemies[i].getHP() <= 0);
				picked = enemies[i];
			}
			
			// TODO: only 1 ability used with enemies
			Ability pickedAb = this.abilities.get(0);
			int dmg = r.nextInt(pickedAb.getMaxDmg()-pickedAb.getMinDmg())+pickedAb.getMinDmg();
			if(r.nextFloat() <= picked.getDodgeChance()) {
				new Indicator("dodged", picked.getX(), picked.getY(), 255, 255, 255);
			} else {
				picked.setHP(picked.getHP()-dmg);
				new Indicator("-"+dmg, picked.getX(), picked.getY());
			}
		}
		else {
			for(Entity picked: enemies) {
				Ability pickedAb = this.abilities.get(0);
				int dmg = r.nextInt(pickedAb.getMaxDmg()-pickedAb.getMinDmg())+pickedAb.getMinDmg();
				picked.setHP(picked.getHP()-dmg);
				new Indicator("-"+dmg, picked.getX(), picked.getY());
			}
		}
		this.attacking = true;
		Game.level.lastAttackTime = System.currentTimeMillis();
		Game.level.turnEnded = true;
		Game.game.player.checkDeads();
	}
	
	public void fixedUpdate() {
		
	}
}
