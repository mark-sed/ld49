package com.sedlacek.ld49.enemies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.abilities.AttackAbility;
import com.sedlacek.ld49.graphics.Animation;
import com.sedlacek.ld49.graphics.ImageLoader;
import com.sedlacek.ld49.graphics.SpriteSheet;
import com.sedlacek.ld49.main.CollisionBox;
import com.sedlacek.ld49.main.Config;

public class Slime extends Enemy {

	private Random r = new Random();
	
	public Slime(int position, int room) {
		this.name = "Slime";
		this.type = "Mutant";
		this.room = room;
		try {
			anim_string = Files.readString(Paths.get("res/slime_anim.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.idleAnim = new Animation(anim_string, "idle");
		this.idleCombatAnim = idleAnim;
		this.attackAnim = new Animation(anim_string, "attack");
		this.attackAnim.setOneTime(true);
		this.widestAnim = idleAnim.getWidth();
		this.currAnim = idleAnim;
		this.w = currAnim.getWidth()*Config.SIZE_MULT;
		this.h = 9*Config.SIZE_MULT;
		this.position = position;
		this.updatePosition(position);
		y += Config.SIZE_MULT*7;
		
		this.abilities = new ArrayList<Ability>();
		this.abilities.add(new AttackAbility(2+(room/5), 4+(room/5), 1+(room/5), this, true));
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/slime.png"));
		this.icon = ss.grabImage(1, 1, 5, 5, 16);
		
		this.collisionBox = new CollisionBox(this, idleAnim.getWidth()*Config.SIZE_MULT, idleAnim.getHeight()*Config.SIZE_MULT);
		
		this.enemy = true;
		
		int minPosHP = 4+(room/5);
		int maxPosHP = 7+(room/5);
		
		this.dodgeChance = r.nextFloat() * (0.1f);
		
		this.maxHP = r.nextInt(maxPosHP-minPosHP)+minPosHP;
		this.HP = maxHP;
		hpBarOffset = 40;
	}
}
