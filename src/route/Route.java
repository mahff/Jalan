package route;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class Route {
	
	ArrayList<String> nodes = new ArrayList<String>();
	String type;
	int time;
	ArrayList<String> visitedNodes = new ArrayList<String>();

	public Route(){
		time = 0;
	}
	
	public boolean isNodeVisited(String node){
		return visitedNodes.contains(node);
	}
	
	public void addNode(String data){
		nodes.add(data);
	}
	
	public void addTime(double d){
		this.time += d;
	}
	
	public void clear(){
		nodes.clear();
	}
	
	public abstract double getTimeWeight(String data, String bounds) throws ParseException;
	public abstract double getPriceWeight(String data, String bounds);
	
	public ArrayList<String> getNodes(){
		return nodes;
	}
	public String getType(){
		return type;
	}
}
