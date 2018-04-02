package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4584388369897487885L;

	public static final int WIDTH=800, HEIGHT=WIDTH/12*9, CENTERW=WIDTH/2, CENTERH=HEIGHT/2;
	private Thread thread;
	private boolean running=false;

	protected Handler handler;
	private int level = -1;
	private String gameState = "gamePlay";
	private int countdown = 300;
	private boolean clear = true;
	private Random ran = new Random();
	private ArrayList<HealthBar> bars = new ArrayList<HealthBar>();

	public Game(){
		new Window(WIDTH, HEIGHT, "Save Earth", this);
		handler = new Handler();
		loadImages();
		if(gameState.equals("gamePlay"))
		{
			handler.addObject(new Earth((Game.WIDTH-150)/2, (Game.HEIGHT-150)/2,earth, ID.Earth)); 
			handler.addObject(new Moon((CENTERW-75-50), (CENTERH-75-50),moon, ID.Moon));
			drawHealthBar();
		}

	}

	public synchronized void start(){
		thread= new Thread(this);
		thread.start();
		running=true;
	}

	public synchronized void stop(){
		try{
			thread.join();
			running=false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run(){
		long lastTime= System.nanoTime();
		double amountOfTicks= 60.0;
		double ns= 1000000000 / amountOfTicks;
		double delta =0;
		long timer = System.currentTimeMillis();
		int frames=0;
		while(running){
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			while(delta>=1){
				tick();
				delta--;
			}
			if(running){
				render();
			}
			frames++;

			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
				System.out.println("FPS:  "+ frames);
				frames=0;
			}
		}
		stop();
	}

	private void tick(){
		if(gameState.equals("menu"))
		{
			handler.tick();
			runMenu();
		} else if (gameState.equals("gamePlay"))
		{
			handler.tick();
			runGame();
		}
	}

	private void render(){
		if(gameState.equals("menu"))
		{
			displayMenu();
		} else if (gameState.equals("gamePlay"))
		{
			displayGame();
		}
	}
	public static void main(String args[]){
		new Game();
	}

	//checks for collisions
	public void update()
	{
		for (GameObject one : handler.object)
		{
			for (GameObject two : handler.object)
			{
				if (one.isCollision(two))
				{
					one.addCollision(two);
					two.addCollision(one);
				}
			}
		}
	}

	//removes dead objects
	public void remove()
	{
		for(int x = 0; x < handler.getSize(); x++)
		{
			if(!handler.get(x).isAlive)
			{
				handler.removeObject(handler.get(x));
			}
		}

	}

	//checks for enemies in the game
	boolean stageIsClear()
	{
		for (int x = 0; x < handler.getSize(); x++)
		{
			if (handler.get(x) instanceof Enemy)
			{
				return false;
			}
		}
		return true;
	}
	//checks if stage is clear then moves on to the next round/level
	void advanceLevel()
	{
		if (stageIsClear())
		{
			level++;
			spawnEnemies();
		}
	}
	
	//Counts the amount of enemies in the game
	public int countEnemies()
	{
		int num = 0;
		for(int x = 0; x < handler.getSize(); x++)
		{
			if(handler.get(x) instanceof Enemy)
			{
				num++;
			}
		}
		return num;
	}

	//Spawns enemies at the start of each round
	public void spawnEnemies()
	{
		if(clear)
		{
			if(level == 0) 
			{
				spawnAsteroid(5);
			}
			else if(level == 1)
			{
				spawnAsteroid(8);
			}else if(level == 2)
			{
				spawnAsteroid(10);
			}else if(level == 3)
			{
				spawnAsteroid(20);
			}
		}
	}

	//Spawns num amount of asteroids randomly on different sides of maps
	public void spawnAsteroid(int num)
	{
		for(int x = 0; x < num; x++)
		{
			int side = ran.nextInt(4) + 1;
			int randomDir;
			if((int)(Math.random()*10) < 5)
			{
				randomDir = -1;
			} else {
				randomDir = 1;
			}
			if(side == 1)
			{
				handler.addObject(new Asteroid(-32, (int)(Math.random() * Game.HEIGHT), 32, 1, randomDir,asteroid,ID.Asteroid));
			} else if(side == 2)
			{
				handler.addObject(new Asteroid(Game.WIDTH + 32, (int)(Math.random() * Game.HEIGHT), 32, -1, randomDir,asteroid, ID.Asteroid));
			} else if(side == 3)
			{
				handler.addObject(new Asteroid((int)(Math.random() * Game.WIDTH), -32, 32, randomDir, 1,asteroid, ID.Asteroid));
			} else if(side == 4)
			{
				handler.addObject(new Asteroid((int)(Math.random() * Game.WIDTH), Game.HEIGHT+32, 32, randomDir, -1,asteroid, ID.Asteroid));
			}

		}
	}
	//Grabs the earth puts it into the bars arraylist that way its health bar's render method can be accessed
	public void drawHealthBar()
	{
		for(int x = 0; x < handler.getSize(); x++)
		{
			GameObject o = handler.get(x);
			if(o instanceof Earth)
			{
				bars.add(new HealthBar(o, 25, 50, 100, 20, Color.GREEN));
			}
			if(o instanceof Moon)
			{
				bars.add(new HealthBar(o, 25, 75, 100, 20, Color.blue));
			}
		}
	}

	//place Holder to show gameStates
	public void displayMenu()
	{
		BufferStrategy bs= this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Game will start in: " + countdown, WIDTH/2-50, HEIGHT/2);
		g.dispose();
		bs.show();
	}
	public void runMenu()
	{
		if(countdown > 0)
		{
			countdown--;
		}
		if(countdown == 0)
		{
			gameState = "gamePlay";
			handler.addObject(new Earth((Game.WIDTH-64)/2, (Game.HEIGHT-64)/2, earth,ID.Earth)); 
		
			handler.addObject(new Moon((CENTERW-75-50), (CENTERH-75-50), moon, ID.Moon));
			drawHealthBar();
		}
	}

	//displays the game
	public void displayGame()
	{
		BufferStrategy bs= this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g= bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		if(!bars.isEmpty())
		{
			for(int x = 0; x < bars.size(); x++)
			{
				bars.get(x).render(g);
			}
		}
		g.setColor(Color.WHITE);
		g.drawString("Level: " + level, WIDTH-200, 25);
		g.drawString("Enemies: " + countEnemies(), WIDTH-200, 50);

		g.dispose();
		bs.show();
	}
	//Allows for actions when the gameState is on gameplay
	public void runGame()
	{
		handler.tick();
		remove();
		update();
		advanceLevel();
	}
	
	//loads all the images
	public void loadImages()
	{
		try {
			moon = ImageIO.read(new File("images/Moonpix2.png"));
			earth = ImageIO.read(new File("images/Earthpix2.png"));
			asteroid = ImageIO.read(new File("images/rock1.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public BufferedImage moon;
	public BufferedImage earth;
	public BufferedImage asteroid;

}
