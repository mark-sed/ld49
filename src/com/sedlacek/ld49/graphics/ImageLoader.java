package com.sedlacek.ld49.graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public BufferedImage load(String path){
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage loadNonStatic(String path){
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage loadNS(String path){
		ImageLoader loader = new ImageLoader();
		return loader.loadNonStatic(path);
	}
	
	
	public BufferedReader loadTxt(String path){
		InputStream is= getClass().getResourceAsStream(path);
		InputStreamReader fr = new InputStreamReader(is);
		return new BufferedReader(fr);
	}
	
}
