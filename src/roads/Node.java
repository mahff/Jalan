package roads;

public class Node {

	public double longitude; 
	public double latitude;
	
	public Node(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public String toString() {
		return "Node [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude() {
		return latitude;
	} 
	
}
