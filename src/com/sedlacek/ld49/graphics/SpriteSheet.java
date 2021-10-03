package com.sedlacek.ld49.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet{
	
	private BufferedImage image;
	
	public SpriteSheet (BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height, int tilesize){
		BufferedImage img = image.getSubimage((col*tilesize) - tilesize, (row*tilesize)-tilesize, width, height);
		return img;
	}
	
	public BufferedImage grabItemImage(int col, int row, int width, int height){
		BufferedImage img = image.getSubimage((col*64) - 64, (row*64)-64, width, height);
		return img;
	}
	
	public BufferedImage cropImage(int x, int y, int width, int height){
		BufferedImage img = image.getSubimage(x, y, width, height);
		return img;
	}
}
