package route;

import java.text.ParseException;

public class FerryRoute extends PublicRoute {

	private String type = "ferry";
	
	public FerryRoute() throws ParseException {
		super(17,"08:00","21:00", 65, 20);
	}
	
	public double getPrice(){
		return 12+Math.random()*10;
	}
	
	public double getDistanceTo(String data){
		return 0;
	}
}
