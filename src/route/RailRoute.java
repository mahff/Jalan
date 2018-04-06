package route;

import java.text.ParseException;

public class RailRoute extends PublicRoute {

	private String type = "rail";
	
	public RailRoute() throws ParseException{
		super(2.5,"05:50","23:46", 3, 2);
	}
	
	public double getDistanceTo(String data){
		return 0;
	}

}
