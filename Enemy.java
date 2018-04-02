package game;

import java.awt.Graphics;

public class Enemy extends GameObject{

	//Enemy constructor
	public Enemy(int x, int y, int w, int h, ID id) {
		super(x, y, w, h, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		act();
	}

	//Checks what collision happened and what the result will be
	public void reactions() {
		// TODO Auto-generated method stub
		for (int x = 0; x < collisions.size (); x++)
	    {
	      if (!collisions.isEmpty())
	      {
	        GameObject o = collisions.get(x);
	        if (o instanceof Earth)
	        {
	        	takeDamage(o.getDamage());
	        }
	        if(o instanceof Moon)
	        {
	        	takeDamage(o.getDamage());
	        	
	        }
	        if (o instanceof Asteroid)
	        {
	        	takeDamage(o.getDamage());
	        }
	      }
	    }
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
