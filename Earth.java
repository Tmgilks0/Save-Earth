package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Earth extends GameObject {

	BufferedImage earth;
	
	//Earth's constructor
	public Earth(int x, int y, BufferedImage img, ID id) {
		super(x, y, 150, 150, id);
		maxHealth = 100;
		curHealth = 100;
		damage = 100;
		earth = img;
	}

	//Earth's actions that happen every frame
	public void tick() {
		act();
		
	}

	//draws the planet
	public void render(Graphics g) {
//		try {
//			earth = ImageIO.read(new File("images/Earthpix2.png"));
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		g.drawImage(earth, (int)x, (int)y,null);
	}

	//Takes damage when a collision is made 
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
	        }
	      }
	    }
	}

}

