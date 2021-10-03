package com.sedlacek.ld49.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.sedlacek.ld49.abilities.Ability;

public class AbilityButton extends GUI {

	private Ability a;
	
	public AbilityButton(Ability a) {
		this.x = 0;
		this.y = 0;
		this.w = 64;
		this.h = 64;
		this.a = a;
	}

	@Override
	public void render(Graphics g) {
		if(a.getIcon() == null)
			return;
					
		g.drawImage(a.getIcon(), x, y, w, h, null);
		if(mouseOver) {
			g.setColor(new Color(255,255,255,50));
			g.fillRect(x, y, w, h);
		}
		
	}
	
	public void render(Graphics g, int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		if(a.getIcon() == null)
			return;
					
		g.drawImage(a.getIcon(), x, y, w, h, null);
		if(mouseOver && !disableClick) {
			g.setColor(new Color(255,255,255,50));
			g.fillRect(x, y, w, h);
		}
		
	}

	@Override
	public void clicked() {
		a.clicked();
	}

	public Ability getA() {
		return a;
	}

	public void setA(Ability a) {
		this.a = a;
	}
	
	

}
