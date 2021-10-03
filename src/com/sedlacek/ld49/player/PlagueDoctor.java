package com.sedlacek.ld49.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.abilities.AttackAbility;
import com.sedlacek.ld49.abilities.HealAbility;
import com.sedlacek.ld49.abilities.SkipTurnAbility;
import com.sedlacek.ld49.graphics.Animation;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.main.CollisionBox;
import com.sedlacek.ld49.main.Config;
import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.Game.LevelState;

public class PlagueDoctor extends Character {
	
	public PlagueDoctor(int position) {
		this.type = "Human";
		this.name = "Doc";
		try {
			anim_string = Files.readString(Paths.get("res/plaguedoc_anim.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.idleAnim = new Animation(anim_string, "idle");
		this.moveAnim = new Animation(anim_string, "walking");
		this.idleCombatAnim = idleAnim;
		this.attackAnim = new Animation(anim_string, "attack");
		this.attackAnim.setOneTime(true);
		this.unstableAttackAnim = new Animation(anim_string, "attackUnstable");
		this.unstableAttackAnim.setOneTime(true);
		this.unstableIdleAnim = new Animation(anim_string, "idleUnstable");
		this.unstableWalkingAnim = new Animation(anim_string, "walkingUnstable");
		this.widestAnim = 13;
		this.currAnim = idleAnim;
		this.w = currAnim.getWidth()*Config.SIZE_MULT;
		this.h = currAnim.getHeight()*Config.SIZE_MULT;
		this.position = position;
		
		this.stableAbilities = new ArrayList<Ability>();
		this.stableAbilities.add(new AttackAbility(1, 2, 1, this, false));
		this.stableAbilities.add(new HealAbility(0, 6, 1, this, false));
		this.stableAbilities.add(new SkipTurnAbility(0,0,1,this, false));
		this.unstableAbilities = new ArrayList<Ability>();
		this.unstableAbilities.add(new AttackAbility(2, 4, 1, this, false));
		this.unstableAbilities.add(new SkipTurnAbility(0,0,1,this, false));
		Ability healAb = new HealAbility(5, 9, 1, this, false);
		healAb.setAoe(true);
		this.unstableAbilities.add(healAb);
		
		this.abilities = this.stableAbilities;
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/plaguedoc.png"));
		this.icon = ss.grabImage(2, 2, 5, 5, 16);
		
		this.collisionBox = new CollisionBox(this, idleAnim.getWidth()*Config.SIZE_MULT, idleAnim.getHeight()*Config.SIZE_MULT);
		
		this.updatePosition(position);
		this.maxHP = 20;
		this.HP = maxHP;
		hpBarOffsetY = 15;
		
		this.dodgeChance = 0.2f;
		
		this.coolDownAmount = 2;
		
		this.maxUnstable = 20;
		this.unstable = 0;
	}

	@Override
	public void update() {
		super.update();
		
		if(moving) {
			if(this.isUnstable())
				this.currAnim = unstableWalkingAnim;
			else
				this.currAnim = moveAnim;
		}
		else {
			if(Game.levelState == LevelState.COMBAT) {
				if(this.currAnim == this.attackAnim || this.currAnim == this.unstableAttackAnim) {
					if(this.currAnim.isDone()) {
						this.currAnim = this.idleCombatAnim;
						this.attackAnim.reset();
						this.unstableAttackAnim.reset();
						this.attacking = false;
					}
				}
				else if(this.attacking) {
					if(this.isUnstable())
						this.currAnim = this.unstableAttackAnim;
					else
						this.currAnim = this.attackAnim;
				}
				else {
					if(this.isUnstable())
						this.currAnim = this.unstableIdleAnim;
					else
						this.currAnim = this.idleCombatAnim;
				}
			}
			else {
				if(this.isUnstable())
					this.currAnim = this.unstableIdleAnim;
				else
					this.currAnim = this.idleAnim;
			}
		}
		
		this.w = currAnim.getWidth()*Config.SIZE_MULT;
		this.y = Config.HEIGHT - 29*4 - currAnim.getHeight() * Config.SIZE_MULT + Game.level.rooms[Game.level.currentRoom].y;
		this.collisionBox = new CollisionBox(this, currAnim.getWidth()*Config.SIZE_MULT, currAnim.getHeight()*Config.SIZE_MULT);
	}

	@Override
	public void fixedUpdate() {
		// TODO Auto-generated method stub
		
	}

}
