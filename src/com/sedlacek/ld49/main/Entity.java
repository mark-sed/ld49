package com.sedlacek.ld49.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.enemies.Enemy;
import com.sedlacek.ld49.graphics.Animation;
import com.sedlacek.ld49.graphics.Indicator;

public abstract class Entity extends GameObject {

	protected String name;
	protected String type;
	protected String anim_string;
	
	protected int position;
	
	protected int hpBarOffset, hpBarOffsetY;
	
	protected int maxHP;
	protected int HP;
	
	protected boolean attacking;
	
	protected float critChance;
	protected float dodgeChance;
	
	protected Animation currAnim;
	protected Animation idleAnim;
	protected Animation idleCombatAnim;
	protected Animation attackAnim;
	protected int widestAnim;
	
	protected BufferedImage icon;
	
	protected ArrayList<Ability> abilities;
	
	protected boolean enemy;
	
	private Random r = new Random();
	
	public abstract boolean canBeTargeted(Ability a);
	
	public void update() {
		if(collisionBox != null) {
			collisionBox.update();
		}
	}
	
	public void nextTurnTrigger() {
		
	}
	
	public void nextLevelTriggered() {
		
	}
	
	public void skippedTurn() {
		
	}
	
	public void useAbility(Ability a) {
		if(a.isSkip()) {
			this.skippedTurn();
			return;
		}
		if(a.getMaxDmg() > 0) {
			if(!a.isAoe()) {
				a.getOwner().setAttacking(true);
				
				if(r.nextFloat() <= dodgeChance) {
					new Indicator("dodge", x+20, y+5, 255,255,255);
				}else {
					int dmg = r.nextInt((a.getMaxDmg()+1)-a.getMinDmg())+a.getMinDmg();
					this.HP -= dmg;
					new Indicator("-"+dmg, x+20, y+5);
				}
			}
			else {
				if(this.enemy) {
					for(Enemy e: Game.level.getEnemies()) {
						if(e.getHP() <= 0) {
							continue;
						}
						if(r.nextFloat() <= dodgeChance) {
							new Indicator("dodge", e.x+20, e.y+5, 255,255,255);
						}
						else {
							int dmg = r.nextInt((a.getMaxDmg()+1)-a.getMinDmg())+a.getMinDmg();
							e.HP -= dmg;
							new Indicator("-"+dmg, e.x+20, e.y+5);
						}
					}
				}
			}
		} else {
			a.getOwner().setAttacking(true);
			
			if(!a.isAoe()) {
				int heal = r.nextInt((a.getMaxHeal()+1)-a.getMinHeal())+a.getMinHeal();
				if(this.HP + heal > this.maxHP)
					this.HP = this.maxHP;
				else
					this.HP += heal;
				new Indicator("+"+heal, x+20, y+5, 0, 220, 15);
			}
			else {
				if(!this.enemy) {
					for(com.sedlacek.ld49.player.Character c: Game.game.player.characters) {
						int heal = r.nextInt((a.getMaxHeal()+1)-a.getMinHeal())+a.getMinHeal();
						if(c.HP + heal > c.maxHP)
							c.HP = c.maxHP;
						else
							c.HP += heal;
						new Indicator("+"+heal, c.x+20, c.y+5, 0, 220, 15);
					}
				}
			}
		}
	}
	
	public void attack(Entity ...enemies) {
		
	}
	
	public abstract void fixedUpdate();
	
	public void render(Graphics g) {
		if(HP <= 0)
			return;
		if(currAnim != null) {
			currAnim.runAndDrawAnimation(g, x, y, Config.SIZE_MULT);
		}
		
		float hpMaxWidth = 50;
		float hpWidth = hpMaxWidth / maxHP * HP;
		
		// HP bar
		g.setColor(new Color(184, 19, 7));
		g.fillRect(x-20+hpBarOffset, y-20-hpBarOffsetY, (int)hpWidth, 10);
		g.setColor(Color.gray);
		g.drawRect(x-20+hpBarOffset, y-20-hpBarOffsetY, (int)hpMaxWidth, 10);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnim_string() {
		return anim_string;
	}

	public void setAnim_string(String anim_string) {
		this.anim_string = anim_string;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getHpBarOffset() {
		return hpBarOffset;
	}

	public void setHpBarOffset(int hpBarOffset) {
		this.hpBarOffset = hpBarOffset;
	}

	public int getHpBarOffsetY() {
		return hpBarOffsetY;
	}

	public void setHpBarOffsetY(int hpBarOffsetY) {
		this.hpBarOffsetY = hpBarOffsetY;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public Animation getCurrAnim() {
		return currAnim;
	}

	public void setCurrAnim(Animation currAnim) {
		this.currAnim = currAnim;
	}

	public Animation getIdleAnim() {
		return idleAnim;
	}

	public void setIdleAnim(Animation idleAnim) {
		this.idleAnim = idleAnim;
	}

	public Animation getIdleCombatAnim() {
		return idleCombatAnim;
	}

	public void setIdleCombatAnim(Animation idleCombatAnim) {
		this.idleCombatAnim = idleCombatAnim;
	}

	public Animation getAttackAnim() {
		return attackAnim;
	}
	

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public void setAttackAnim(Animation attackAnim) {
		this.attackAnim = attackAnim;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(ArrayList<Ability> abilities) {
		this.abilities = abilities;
	}

	public float getCritChance() {
		return critChance;
	}

	public void setCritChance(float critChance) {
		this.critChance = critChance;
	}

	public boolean isEnemy() {
		return enemy;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}

	public float getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(float dodgeChance) {
		this.dodgeChance = dodgeChance;
	}
	
	
}
