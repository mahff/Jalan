package roads;

import javafx.scene.paint.Color;

public class Area {
	public boolean walkable;
	public Color color; 
	public String name;
	
	public Area(boolean walkable, Color color, String name) {
		super();
		this.walkable = walkable;
		this.color = color;
		this.name = name;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	

}
