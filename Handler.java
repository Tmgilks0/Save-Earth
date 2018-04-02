package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
	
public class Handler {

	ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	public int getSize(){
		return object.size();
	}
	
	public GameObject get(int pos)
	{
		return object.get(pos);
	}
	
	public void tick() {
			for(int i = 0; i < object.size(); i++){
				GameObject tempObject = object.get(i);
				tempObject.tick();
			}
	}
		
	public void render (Graphics g) {
		for(int i = 0; i < object.size(); i ++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
		
	public void addObject(GameObject object) {
		this.object.add(object);
	}
		
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
