package threading;

import java.util.ArrayList;

import route.Route;
import route.WalkRoute;

public class PathFinder{
	
	private ArrayList<Route> routes;
	private Thread instance;
	
	public void run(){
			instance = new Thread(new Runnable(){
			public void run(){
					ArrayList<Route> path = new ArrayList<Route>();
					System.out.println("Ended first phase");
					WalkRoute route = new WalkRoute();
					route.addNode("yo");
					path.add(route);
					//if(listener!=null) listener.onCheapestRouteFound(new ArrayList<Route>());
					/*synchronized(path){
						path.add(new WalkRoute());
						System.out.println("Réssi cette merde");
					}*/
					QueueHandler.enqueue(path);
					System.out.println("Ended second phase");
				}
	    	});
			instance.start();
		}
	public Runnable getInstance(){
		return instance;
	}

}
