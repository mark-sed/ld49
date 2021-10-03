package com.sedlacek.ld49.gui;

import java.awt.Graphics;

public abstract class GUIObject {

	protected int x, y;
	protected boolean done;
	
	protected abstract void update();
	protected abstract void render(Graphics g);
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}
