package com.sedlacek.ld49.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import com.sedlacek.ld49.abilities.BecomeUnstableItem;
import com.sedlacek.ld49.abilities.GainDMGItem;
import com.sedlacek.ld49.abilities.GainDodgeItem;
import com.sedlacek.ld49.abilities.GainHealItem;
import com.sedlacek.ld49.abilities.GainUnstableDMGItem;
import com.sedlacek.ld49.abilities.GainUnstableHealItem;
import com.sedlacek.ld49.abilities.HealAllItem;
import com.sedlacek.ld49.abilities.LowerUnstableItem;
import com.sedlacek.ld49.main.Config;

public class Shop {

	public final int WIDTH = Config.WIDTH-60;
	public final int HEIGHT = Config.HEIGHT/2-50;
	public int x, y;
	private long lastTime;
	
	private Random r = new Random();
	
	public static final int ITEM_AMOUNT = 3;
	public ItemButton[] items;
	
	public boolean closeShop;
	
	public Shop(int room) {
		this.x = 30;
		this.y = -HEIGHT;
		
		items = new ItemButton[ITEM_AMOUNT];
		do {
			for(int i = 0; i < ITEM_AMOUNT; i++) {
				items[i] = randomItem();
			}
		} while( (items[0].getA().getClass() == items[1].getA().getClass()) ||
				 (items[0].getA().getClass() == items[2].getA().getClass())||
				 (items[1].getA().getClass() == items[2].getA().getClass()));
				
	}
	
	public ItemButton randomItem() {
		switch(r.nextInt(8)){
		case 0: return new ItemButton(new HealAllItem());
		case 1: return new ItemButton(new GainDMGItem());
		case 2: return new ItemButton(new GainUnstableDMGItem());
		case 3: return new ItemButton(new GainHealItem());
		case 4: return new ItemButton(new GainUnstableHealItem());
		case 5: return new ItemButton(new BecomeUnstableItem());
		case 6: return new ItemButton(new LowerUnstableItem());
		case 7: return new ItemButton(new GainDodgeItem());
		default:
			return new ItemButton(new HealAllItem());
		}
	}
	
	public void update() {
		if(!closeShop) {
			if(y < 20 && System.currentTimeMillis()-lastTime >= 50) {
				y += 5;
			}
			for(ItemButton b: items) {
				b.update();
			}
		}else {
			if(y > -HEIGHT && System.currentTimeMillis()-lastTime >= 50) {
				y -= 5;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(173, 132, 17));
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(new Color(173, 132, 17).brighter());
		g.fillRect(x, y, WIDTH, 10);
		g.fillRect(x, y+HEIGHT-10, WIDTH, 10);
		g.fillRect(x, y, 10, HEIGHT);
		g.fillRect(x+WIDTH-10, y, 10, HEIGHT);
		
		items[0].render(g, x+WIDTH/2-220, y+HEIGHT/2-48, 64, 64);
		items[1].render(g, x+WIDTH/2-32, y+HEIGHT/2-48, 64, 64);
		items[2].render(g, x+WIDTH/2+220-64, y+HEIGHT/2-48, 64, 64);
		
		for(ItemButton b: items) {
			if(b.isMouseOver()) {
				g.setFont(new Font("DorFont03", Font.PLAIN, 32));
				g.setColor(Color.white);
				g.drawString(b.getDesc(), Config.WIDTH/2-g.getFontMetrics().stringWidth(b.getDesc())/2, y+HEIGHT-25);
			}
		}
	}
}
