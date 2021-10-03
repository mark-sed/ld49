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
import com.sedlacek.ld49.main.Game;

public class Rat extends Enemy {

	private Random r = new Random();
	
	public Rat(int position, int room) {
		this.name = "Rat";
		this.type = "Animal";
		this.room = room;
		try {
			anim_string = Files.readString(Paths.get("res/rat_anim.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.idleAnim = new Animation(anim_string, "idle");
		this.idleCombatAnim = new Animation(anim_string, "idleCombat");
		this.attackAnim = new Animation(anim_string, "attack");
		this.attackAnim.setOneTime(true);
		this.widestAnim = idleAnim.getWidth();
		this.currAnim = idleAnim;
		this.w = currAnim.getWidth()*Config.SIZE_MULT;
		this.h = 3*Config.SIZE_MULT;
		this.position = position;
		this.updatePosition(position);
		y += Config.SIZE_MULT*13;
		
		this.abilities = new ArrayList<Ability>();
		this.abilities.add(new AttackAbility(2+(room/5), 3+(room/5), 1+(room/5), this, true));
		
		SpriteSheet ss = new SpriteSheet(ImageLoader.loadNS("/rat.png"));
		this.icon = ss.grabImage(1, 1, 5, 5, 16);
		
		this.collisionBox = new CollisionBox(this, idleAnim.getWidth()*Config.SIZE_MULT, idleAnim.getHeight()*Config.SIZE_MULT);
		
		this.enemy = true;
		
		int minPosHP = 3+(room/5);
		int maxPosHP = 5+(room/5);
		
		this.dodgeChance = 0.05f + r.nextFloat() * (0.13f-0.05f);
		
		this.maxHP = r.nextInt(maxPosHP-minPosHP)+minPosHP;
		this.HP = maxHP;
		hpBarOffset = +30;
	}
	
	@Override
	public void update() {
		super.update();
		this.collisionBox.setY(this.y-8*4);
	}
}
