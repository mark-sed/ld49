package com.sedlacek.ld49.main;

import java.awt.*;

public abstract class GameObject {
	
	protected int x,
	  y,
	  w,
	  h;

	public abstract void update();
	public abstract void render(Graphics g);
	public void fixedUpdate(){}
	public void clicked(){};
	protected CollisionBox collisionBox;
	
	protected GameObject(){

	}
	
	//Custom setters
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//Getters and setters
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
	public int getW() {
		return w;
	}
	public void setW(int width) {
		this.w = width;
	}
	public int getH() {
		return h;
	}
	public void setH(int height) {
		this.h = height;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
	public CollisionBox getCollisionBox() {
	return collisionBox;
	}
	public void setCollisionBox(CollisionBox collisionBox) {
	this.collisionBox = collisionBox;
	}
}
