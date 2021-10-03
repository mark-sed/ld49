package com.sedlacek.ld49.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	public static final int S = KeyEvent.VK_S;
	public static final int R = KeyEvent.VK_R;
	public static final int T = KeyEvent.VK_T;
	public static final int ENTER=KeyEvent.VK_ENTER;
	public static final int F1=KeyEvent.VK_F1;
	public static final int ESC=KeyEvent.VK_ESCAPE;
	public static final int M=KeyEvent.VK_M;
	public static final int D=KeyEvent.VK_D;
	
	public static boolean rightSide = false,
							rightSideCtrl = false;
	
	public boolean[] keys = new boolean[525];
	public static boolean anyKeyPressed = false;
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		anyKeyPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
				rightSide = true;
			else
				rightSide = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT)
				rightSideCtrl = true;
			else
				rightSideCtrl = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		anyKeyPressed = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
