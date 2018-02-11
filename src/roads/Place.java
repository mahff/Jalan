package roads;

public class Place extends Node{

	public String name; 
	public String type;
	public Place(double longitude, double latitude, String name, String type) {
		super(longitude, latitude);
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	
	
}
