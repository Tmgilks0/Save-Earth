package game;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar  {
	int x, y, w, h;
	GameObject o;
	Color c;
	//Constructor for Healthbar 
	public HealthBar(GameObject o, int x, int y, int w, int h, Color c)
	{
		this.o = o;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
	}

	//draws a health bar using the parameters of HealthBar
	public void render(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		if(o.getCurHealth()/o.getMaxHealth() > .25)
		{
			g.setColor(c);
			g.fillRect(x, y, (int)(o.getCurHealth()/o.getMaxHealth()*w), h);
		} else {
			g.setColor(Color.RED);
			g.fillRect(x, y, (int)(o.getCurHealth()/o.getMaxHealth()*w), h);
		}
	}


}
