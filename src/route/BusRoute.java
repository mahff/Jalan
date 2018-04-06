package route;

import java.text.ParseException;

public class BusRoute extends PublicRoute {
	
	private String type = "bus";
	
	public BusRoute() throws ParseException {
		super(2.5,"06:55","21:32", 5, 3);
	}
	
	public double getDistanceTo(String data){
		return 0;
	}
}
