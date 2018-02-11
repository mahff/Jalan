package data;

public class Route {
	
	public Location[] waypoints;
	
	public Route(Location... waypoints) {
		this.waypoints = waypoints;
	}
}