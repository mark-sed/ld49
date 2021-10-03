package com.sedlacek.ld49.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class CollisionBox{

	private final int WIDTH = 5,
					  HEIGHT = 5;
	
	private int w, h, xOffset, yOffset;
	private boolean staticObject;
	private GameObject go;
	
	private Rectangle box;
	private boolean notCollidable;

	public static ArrayList<CollisionBox> collisionStatic = new ArrayList<CollisionBox>();
	public static ArrayList<CollisionBox> collisionGO= new ArrayList<CollisionBox>();
	
	public CollisionBox(GameObject go, int w, int h){
		collisionGO.add(this);
		this.go = go;
		this.staticObject = false;
		this.w = w;
		this.h = h;
		this.xOffset = 0;
		this.yOffset = 0;
		this.box = new Rectangle(go.getX(), go.getY(), w, h);
	}
	
	public CollisionBox(GameObject go, int w, int h, int xOffset, int yOffset){
		collisionGO.add(this);
		this.go = go;
		this.staticObject = false;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.w=w-xOffset;
		this.h=h-yOffset;
		this.box = new Rectangle(go.getX(), go.getY(), this.w, this.h);
	}
	
	public CollisionBox(int x, int y, int w, int h){
		collisionStatic.add(this);
		this.staticObject = true;
		this.w = w;
		this.h = h;
		this.xOffset = 0;
		this.yOffset = 0;
		this.box = new Rectangle(x, y, w, h);
	}
	
	public CollisionBox(boolean attacBox, int x, int y, int w, int h){
		this.staticObject = true;
		this.w = w;
		this.h = h;
		this.xOffset = 0;
		this.yOffset = 0;
		this.box = new Rectangle(x, y, w, h);
	}
	
	public void update(){
		if(!staticObject){
			if(go == null){
				remove();
				return;
			}
				
			box.x = go.getX()+xOffset;
			box.y = go.getY()+yOffset;
		}
	}
	
	public void debug(Graphics g){
		g.setColor(Color.RED);
		g.drawRect(box.x, box.y, box.width, box.height);
	}
	
	public boolean isCollision(){
		if(notCollidable)
			return false;
		return isCollision(this.box);
	}
	
	public static boolean isCollision(Rectangle cBox){ 
		return isCollision(cBox, new CollisionBox[0]);
	}
	
	public static boolean isCollision(Rectangle cBox, CollisionBox ... exclude){			
		for(CollisionBox c : collisionGO){
			if(c.getBox() == cBox || exclude != null && Arrays.asList(exclude).contains(c))
				continue;
			if(c.getBox().intersects(cBox) && !c.isNotCollidable()){
				return true;
			}
		}
		for(CollisionBox c : collisionStatic){
			if(c.getBox() == cBox || exclude != null && Arrays.asList(exclude).contains(c))
				continue;
			if(c.getBox().intersects(cBox)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isCollision(CollisionBox c2){
		if(getBox().intersects(c2.getBox()))
			return true;
		return false;
	}
	
	public void remove(){
		if(!staticObject)
			collisionGO.remove(this);
		else
			collisionStatic.remove(this);
	}
	
	public boolean isCollUp(CollisionBox ... exclude) {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+WIDTH, box.y, w-WIDTH*2, HEIGHT);
		return isCollision(rec, exclude);
	}
	
	public boolean isCollUp() {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+WIDTH, box.y, w-WIDTH*2, HEIGHT);
		return isCollision(rec, this);
	}
	
	public boolean isCollDown(CollisionBox ... exclude) {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+WIDTH, box.y+h-HEIGHT, w-WIDTH*2, HEIGHT);
		return isCollision(rec, exclude);
	}
	
	public boolean isCollDown() {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+WIDTH, box.y+h-HEIGHT, w-WIDTH*2, HEIGHT);
		return isCollision(rec, this);
	}
	
	public boolean isCollLeft(CollisionBox ... exclude) {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x, box.y+HEIGHT, WIDTH, h-HEIGHT*2);
		return isCollision(rec, exclude);
	}
	
	public boolean isCollLeft() {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x, box.y+HEIGHT, WIDTH, h-HEIGHT*2);
		return isCollision(rec, this);
	}
	
	public boolean isCollRight(CollisionBox ... exclude) {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+w-WIDTH, box.y+HEIGHT, WIDTH, h-HEIGHT*2);
		return isCollision(rec, exclude);
	}
	
	public boolean isCollRight() {
		if(notCollidable)
			return false;
		Rectangle rec = new Rectangle(box.x+w-WIDTH, box.y+HEIGHT, WIDTH, h-HEIGHT*2);
		return isCollision(rec, this);
	}
	
	//Getters and setters
	
	public int getX(){
		return box.x;
	}
	
	public int getY(){
		return box.y;
	}
	
	public void setX(int x){
		this.box.x = x;
	}
	
	public void setY(int y){
		this.box.y = y;
	}
	

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Rectangle getBox() {
		return box;
	}

	public void setBox(Rectangle box) {
		this.box = box;
	}

	public boolean isNotCollidable() {
		return notCollidable;
	}

	public void setNotCollidable(boolean isNotCollidable) {
		this.notCollidable = isNotCollidable;
	}
	
	
}
