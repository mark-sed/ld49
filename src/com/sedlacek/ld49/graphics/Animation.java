package com.sedlacek.ld49.graphics;

import com.sedlacek.ld49.main.XMLReader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int delay, frames;
	private int index = 0;
	private int count = 0;
	private BufferedImage spriteSheet;

	private BufferedImage[] images;
	private int[] widths, heights;
	private long timer, now, lastTime;
	private String name;
	private boolean done, oneTime;

	public Animation(String source, String name){
		this.name = name;
		XMLReader reader = new XMLReader(source, name);
		this.delay = reader.getInt("delay");
		this.spriteSheet = ImageLoader.loadNS(reader.getString("sprite"));

		reader.loadAnimation(this);
		this.done = false;
		this.oneTime = false;

		frames = images.length;
		index = 0;
		timer = 0;
		now = 0;
		count = 0;
		lastTime = System.currentTimeMillis();

	}

	/*public Animation(String name, BufferedImage ... imgs){
		this.name = name;
		
	}*/

	public void runAnimation(){
		if(done){
			return;
		}
		now = System.currentTimeMillis();
		timer += now - lastTime;
		lastTime = now;
		if (timer >= delay) {
			index ++;
			count++;
			timer = 0;
			if(count > frames)
				count=0;

			if (index >= images.length) {
				if(!oneTime)
					index = 0;
				else{
					index=0;

					done=true;
				}
			}
		}

	}


	public void drawAnimation(Graphics g, int x, int y, int multiplier){ //default facing is left
		int w = images[index].getWidth()*multiplier;
		int h = images[index].getHeight()*multiplier;
		g.drawImage(images[index], x, y, w, h, null);
	}

	public void runAndDrawAnimation(Graphics g, int x, int y, int multiplier){
		runAnimation();
		drawAnimation(g, x, y, multiplier);
	}

	public void runAndDrawAnimation(Graphics g, int x, int y, int multiplier, boolean upsidedown){
		runAnimation();
		int w = images[index].getWidth()*multiplier;
		int h = images[index].getHeight()*multiplier;
		if(upsidedown){
			h = -h;
			y+=images[0].getHeight()*multiplier;
		}
		g.drawImage(images[index], x, y, w, h, null);
	}


	public void reset(){
		done = false;
		index = 0;
	}

	public int getWidth(){
		return widths[index];
	}

	public int getHeight(){
		return heights[index];
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	public int[] getHeights() {
		return heights;
	}

	public void setHeights(int[] heights) {
		this.heights = heights;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isOneTime() {
		return oneTime;
	}

	public void setOneTime(boolean oneTime) {
		this.oneTime = oneTime;
	}


}
