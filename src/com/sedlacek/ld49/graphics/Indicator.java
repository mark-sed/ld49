package com.sedlacek.ld49.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.sedlacek.ld49.gui.GUIHandler;
import com.sedlacek.ld49.gui.GUIObject;

public class Indicator extends GUIObject {

	private String text;
	private int speed;
	private long lastTime;
	private int opacity;
	private int r, g, b;
	private Font font;
	
	public Indicator(String text, int x, int y, int r, int g, int b, int speed, Font font) {
		this.init(text, x, y, r, g, b, speed, font);
	}
	
	public Indicator(String text, int x, int y, int r, int g, int b) {
		this.init(text, x, y, r, g, b, 2, new Font("DorFont03", Font.BOLD, 24));
	}
	
	public Indicator(String text, int x, int y) {
		init(text, x, y, 220, 0, 0, 1, new Font("DorFont03", Font.BOLD, 24));
	}
	
	private void init(String text, int x, int y, int r, int g, int b, int speed, Font font) {
		this.text = text;
		this.x = x;
		this.y = y+15;
		this.speed = speed;
		this.font = font;
		this.r = r;
		this.g = g;
		this.b = b;
		opacity = 255;
		GUIHandler.objects.add(this);
	}
	
	public void update() {
		if(System.currentTimeMillis() - lastTime >= 40) {
			y-=speed;
			opacity -= 2;
			if(opacity < 0) {
				opacity = 0;
				this.done = true;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(new Color(this.r, this.g, this.b, opacity));
		g.drawString(text, x, y);
	}
}
