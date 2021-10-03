package com.sedlacek.ld49.gui;

import com.sedlacek.ld49.abilities.Item;
import com.sedlacek.ld49.main.Game;

public class ItemButton extends AbilityButton {

	private Item item;
	
	public ItemButton(Item a) {
		super(a);
		this.item = a;
		
	}
	
	@Override
	public void clicked() {
		Game.level.rooms[Game.level.currentRoom].shop.closeShop = true;
		this.item.use();
	}
	
	public String getDesc() {
		return item.getDesc();
	}
}
