package route;

public abstract class PersonalRoute extends Route{

	private int speed;
	
	public PersonalRoute(int speed) {
		super();
		this.speed = speed;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public double getTimeWeight(String data, String bounds){
		double lat1 = Double.parseDouble(data.split(",")[1])+Double.parseDouble(bounds.split("::")[4].substring(7)), 
				lat2 = Double.parseDouble(this.nodes.get(nodes.size()-1).split(",")[1])+Double.parseDouble(bounds.split("::")[4].substring(7)), 
				lon1 = Double.parseDouble(data.split(",")[2])+Double.parseDouble(bounds.split("::")[3].substring(7)), 
				lon2 = Double.parseDouble(this.nodes.get(nodes.size()-1).split(",")[2])+Double.parseDouble(bounds.split("::")[3].substring(7));
		
		double R = 6378.137;
		double dLongitude = lon2 * Math.PI / 180 - lon1 * Math.PI / 180,
				dLatitude = lat2* Math.PI / 180 - lat1 * Math.PI / 180;
		double rawDistance = Math.pow(Math.sin(dLatitude/2), 2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.pow(Math.sin(dLongitude/2),2);
		double refinedRawDistance = 2 * Math.atan2(Math.sqrt(rawDistance), Math.sqrt(1-rawDistance));
		double distance = 1000*R*refinedRawDistance;

		return distance*speed*16.67;
	}
	
	public double getPriceWeight(String data, String bounds){
		return 0;
	}
}
