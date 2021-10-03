package com.sedlacek.ld49.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.sedlacek.ld49.abilities.Ability;
import com.sedlacek.ld49.main.Entity;
import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.GameObject;

public class Selector {

	private int x;
	private int y;
	private int w;
	private int h;
	private Entity o;
	private Ability ability;
	private Color color;
	
	public Selector() {
		
	}
	
	public void update() {
		if(ability != null) {
			if(Game.getMouseManager().LClicked) {
				if(o != null && o.canBeTargeted(ability)) {
					o.useAbility(ability);
					Game.level.nextTurn();
				}
				ability = null;
				Game.getMouseManager().LClicked = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(o == null)
			return;
		if(color != null) {
			g.setColor(color);
			g.fillRect(o.getX()-10, 0, o.getW()+20, o.getY()+o.getH());
			return;
		}
		
		if(ability != null && !o.canBeTargeted(ability)) {
			return;
		}
		if(ability == null) {
			g.setColor(new Color(255,255,255,30));
		} else if(ability.getMaxDmg() > 0) {
			g.setColor(new Color(190, 0, 0, 50));
		}
		else {
			g.setColor(new Color(0, 190, 0, 50));
		}
		g.fillRect(o.getX()-15, 0, o.getW()+30, o.getY()+o.getH());
		
	}
	
	public void setO(Entity o) {
		this.o = o;
	}

	public Entity getO() {
		return o;
	}

	public Ability getAbility() {
		return ability;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
