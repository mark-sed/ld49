package com.sedlacek.ld49.abilities;

public abstract class Item extends Ability {

	protected boolean oneTimeUse;
	protected String desc;
	
	public abstract void use();

	public boolean isOneTimeUse() {
		return oneTimeUse;
	}

	public void setOneTimeUse(boolean oneTimeUse) {
		this.oneTimeUse = oneTimeUse;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
