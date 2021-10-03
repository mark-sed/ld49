package com.sedlacek.ld49.gui;

import java.awt.Rectangle;
import java.lang.reflect.Method;

import com.sedlacek.ld49.main.Game;
import com.sedlacek.ld49.main.GameObject;

public abstract class GUI extends GameObject {

    protected boolean mouseOver;
    protected Method clickedMethod;
    protected Object invoker;
    protected boolean disableClick;

    @Override
    public void update() {
        if(Game.getMouseRect().intersects(new Rectangle(x, y, w, h))){
            this.mouseOver = true;
            if(Game.getMouseManager().LClicked){
            	if(!disableClick) {
            		this.clicked();
            	}
                Game.getMouseManager().LClicked = false;
            }
        }else{
            this.mouseOver = false;
        }
    }

    public void clicked(){
    }

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isDisableClick() {
		return disableClick;
	}

	public void setDisableClick(boolean disableClick) {
		this.disableClick = disableClick;
	}
}
