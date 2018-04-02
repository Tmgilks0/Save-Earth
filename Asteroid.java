package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Asteroid extends Enemy{

	private int w, h, xDir, yDir;
	private int speedFactor = 200;
	private BufferedImage asteroid;
	
	//Constructor for Asteroid
	public Asteroid(int x, int y, int size, int xDir, int yDir, BufferedImage img, ID id) {
		super(x, y, size, size, id);
		this.xDir = xDir;
		this.yDir = yDir;
		this.w = size;
		this.h = size;
		velX = (int)(((Math.random() * speedFactor) * xDir)/size)+1;
		velY = ((int)((Math.random() * speedFactor) * yDir)/size)+1;
		maxHealth = size;
		curHealth = maxHealth;
		damage = 10;
		asteroid = img;
	}
	
	//Asteroid's actions for every frame
	public void tick() {
		act();
		movement();
		wrap();
	}

	//Drawing the Asteroid
	public void render(Graphics g) {
//		try {
//			asteroid = ImageIO.read(new File("images/rock1.png"));
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		g.drawImage(asteroid, (int)x, (int)y, w, h,null);
	}

	//RN uses the enemy's reaction method
	public void reactions() {
		// TODO Auto-generated method stub
		super.reactions();
	}

	//movement for the Asteroid 
	public void movement()
	{
		x = x + velX;
		y = y + velY;
	}
	//wraps the Asteroid to the other side of the screen when going off the screen
	public void wrap()
	  {
	    if (x > Game.WIDTH + w)
	    {
	      x = 0 - w;
	    } 

	    if (x < 0 - w)
	    {
	      x = Game.WIDTH + w;
	    }

	    if (y > Game.HEIGHT + h)
	    {
	      y = 0 - h;
	    } 

	    if (y < 0 - h)
	    {
	      y = Game.HEIGHT + h;
	    }
	  }
}
