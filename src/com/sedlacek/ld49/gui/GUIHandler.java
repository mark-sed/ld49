package com.sedlacek.ld49.gui;

import java.awt.Graphics;
import java.util.ArrayList;

public class GUIHandler {

	public static ArrayList<GUIObject> objects = new ArrayList<GUIObject>();
	
	public GUIHandler() {
	}
	
	public static void update() {
		for(int i = 0; i < objects.size(); i++) {
			GUIObject go = objects.get(i);
			go.update();
			if(go.isDone()) {
				objects.remove(go);
				i--;
			}
		}
	}
	
	public static void render(Graphics g) {
		for(GUIObject go: objects) {
			go.render(g);
		}
	}
}
