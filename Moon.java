package game;


import java.awt.Color;
import java.lang.Math;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Moon extends GameObject {
	double angle=0.0;
	BufferedImage moon;
	public Moon(int x, int y, BufferedImage img, ID id) {
		super(x, y, 50, 50, id);
		maxHealth = 50;
		curHealth = 50;
		damage = 50;
		moon = img;
	}
	
	public void tick() {
		act();
		angle+=0.025;
		x=(int)(Game.CENTERW-25+(125*Math.cos(angle)));
		y=(int)(Game.CENTERH-25+(125*Math.sin(angle)));
	}

	public void render(Graphics g) {
//		try {
//			moon = ImageIO.read(new File("images/Moonpix2.png"));
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		g.drawImage(moon, (int)x, (int)y,null);
	}
	@Override
	public void reactions() {
		// TODO Auto-generated method stub
		for (int x = 0; x < collisions.size (); x++)
	    {
	      if (!collisions.isEmpty())
	      {
	        GameObject o = collisions.get(x);
	        if (o instanceof Asteroid)
	        {
	        	takeDamage(o.getDamage());
	        	System.out.println("OOF");
	        }
	      }
	    }
	}

}


