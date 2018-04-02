package game;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class GameObject {

	protected float x, y, w, h;
	protected float curHealth, maxHealth, damage;
	protected boolean isAlive, invulnerable;
	protected ID id;
	protected int velX, velY, timer;
	protected ArrayList<GameObject> collisions;


	public GameObject(int x, int y,int w, int h, ID id) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.id = id;
		curHealth = 1;
		maxHealth = 1;
		timer = 0;
		isAlive = true;
		collisions = new ArrayList<GameObject>();
	}

	public abstract void tick();
	public abstract void reactions();
	public abstract void render(Graphics g);

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getVelX() {
		return velX;
	}
	public int getVelY() {
		return velY;
	}
	public void stop()
	{
		this.velX = 0;
		this.velY = 0;
	}
	public void reverse()
	{
		this.velX *= -1;
		this.velY *= -1;
	}
	public void reverseX()
	{
		this.velX *= -1;
	}
	public void reverseY()
	{
		this.velY *= -1;
	}
	public float getCurHealth()
	{
		return curHealth;
	}
	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	boolean containsPoint(float xPos, float yPos)
	{   
		return xPos >= x && xPos <= x+w && yPos >= y && yPos <= y+h;
	}   

	boolean isCollision(GameObject o)
	{
		if (this == o) return false;

		if (containsPoint(o.x, o.y))      return true;
		if (containsPoint(o.x+o.w, o.y))    return true;
		if (containsPoint(o.x, o.y+o.h))    return true;
		if (containsPoint(o.x+o.w, o.y+o.h))  return true;

		return false;
	}

	public float getDamage()
	{
		return damage;
	}

	public void takeDamage(float amount)
	{
		curHealth -= amount;
		if (curHealth < 0)
		{
			die();
		}
	}

	void act()
	{
		if (!collisions.isEmpty())
		{
			reactions();
			collisions.clear();
		}
		timer++;
	}

	void addCollision(GameObject o)
	{
		for (GameObject c : collisions)
		{
			if (o == c)
			{
				return;
			}
		}
		collisions.add(o);
	}

	void die()
	{
		curHealth = 0;
		isAlive = false;
	}

}