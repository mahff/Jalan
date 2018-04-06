package route;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

import parsing.CSVDocument;
import parsing.JALDocument;

public class RouteFinder{
	
	public RouteFinder(){
		
	}
	
	public ArrayList<Route> findQuickest(String[] transportModes, ArrayList<String> keyPoints, String priority) throws IOException, XMLStreamException, ParseException{
		
		//Getting bounds
		JALDocument jalDocument = new JALDocument("data/maps/singapore.jal");
		String bounds = jalDocument.getElementsDataByType("meta").get(1);
		
		//Initialization of variables
		ArrayList<Route> path = new ArrayList<Route>();
		CSVDocument document = new CSVDocument("data/indexation/singapore.rel");
		
		for(int i=0;i<document.getNodes().size()-2;i++){
			//Initializing both ends
			ArrayList<String> 	startMember = new ArrayList<String>(),
							   	endMember = new ArrayList<String>(),
							   	startVisitedNodes = new ArrayList<String>();
			int k = 1, l =1;
			
			startMember.add(document.getNodes().get(keyPoints.get(i)));
			path.add(new WalkRoute());
			path.get(1).addNode(keyPoints.get(i).substring(keyPoints.get(i).indexOf(",")+1));
			path.add(new WalkRoute());
			path.get(1).addNode(keyPoints.get(i+1).substring(keyPoints.get(i+1).indexOf(",")+1));
			endMember.add(document.getNodes().get(keyPoints.get(i)));
			startVisitedNodes = new ArrayList<String>();
			
			while(startMember.get(startMember.size()-1)!=endMember.get(1)){
				ArrayList<String> startModes = new ArrayList<String>();
				ArrayList<String> endModes = new ArrayList<String>();
				String[] startConnections = document.getNodes().get(startMember.get(startMember.size()-1)).split("::");
				String[] endConnections = document.getNodes().get(endMember.get(endMember.size()-1)).split("::");
				for(String mode : transportModes){
					if(startMember.get(startMember.size()-1).contains(mode)) startModes.add(mode);
					if(endMember.get(endMember.size()-1).contains(mode)) endModes.add(mode);
				}
				ArrayList<String> startNeighbours = new ArrayList<String>();
				ArrayList<String> endNeighbours = new ArrayList<String>();
				
				//Check for neighbours with appropriate transport methods and get rid of already visited nodes
				for(int j=0;j<startConnections.length;j++){
					String relatedNode = document.getNodes().get(startConnections[j].substring(0,startConnections[j].indexOf(",")));
					for(String mode : startModes)
						if(relatedNode.indexOf(mode)!=-1&&!startVisitedNodes.contains(relatedNode))
							startNeighbours.add(relatedNode);
				}
				for(int j=0;j<endConnections.length;j++){
					String relatedNode = document.getNodes().get(startConnections[j].substring(0,startConnections[j].indexOf(",")));
					for(String mode : startModes)
						if(relatedNode.indexOf(mode)!=-1&&!startVisitedNodes.contains(relatedNode))
							endNeighbours.add(relatedNode);
				}

				//Dead end situation
				if(startNeighbours.isEmpty()){
					//Do sth to go back from dead end
				}
				if(endNeighbours.isEmpty()){
					
				}
				else{
					//Choose lowest weighted node
					double currentWeight = Integer.MAX_VALUE;
					String currentNeighbour = "none";
					String currentMode = "none";
					for(String mode : startModes){
						for(String neighbour : startNeighbours){
							double weight = path.get(k/2+1).getTimeWeight(neighbour, bounds);
							if(weight < currentWeight&&startConnections[1].contains(mode)){
								currentWeight = weight;
								currentNeighbour = neighbour;
								currentMode = mode;
							}
						}
					}
					if(currentMode==path.get(k).getType()){
						path.get(k).addNode(currentNeighbour);
						path.get(k).addTime(currentWeight);
					}
					else{
						k++;
						path.get(k).addNode(currentNeighbour);
					}
					
					//Update the route
					startMember.add(currentNeighbour);
					
					currentWeight = Integer.MAX_VALUE;
					for(String mode : endModes){
						for(String neighbour : endNeighbours){
							double weight = path.get(k).getTimeWeight(neighbour, bounds);
							if(weight < currentWeight&&startConnections[1].contains(mode)){
								currentWeight = weight;
								currentNeighbour = neighbour;
								currentMode = mode;
							}
						}
					}
					if(currentMode==path.get(l).getType()){
						path.get(l).addNode(currentNeighbour);
						path.get(l).addTime(currentWeight);
					}
					else{
						l++;
						path.get(l).addNode(currentNeighbour);
					}
					
					//Update the route
					endMember.add(currentNeighbour);
				}
			}
			
		}
		
		return path;
	}
	
	public ArrayList<String> divide(ArrayList<String> input, JALDocument document) throws FileNotFoundException, XMLStreamException{
		ArrayList<String> subpaths = new ArrayList<String>();
		
		//Getting nodes that are entry points to motorways
		ArrayList<String> layerPortals = new ArrayList<String>();
		ArrayList<String> ways = document.getElementsDataByType("way");
		
		
		for(String way : ways){
			if(way.indexOf("subnode=!#")!=-1){
				if(way.substring(way.indexOf("subnode=!#")).indexOf("::")!=-1)
					layerPortals.add(way.substring(way.indexOf("subnode=!#")+10,way.substring(way.indexOf("subnode=!#")+10).indexOf("::")+way.substring(0,way.indexOf("subnode=!#")+10).length()));
				else
					layerPortals.add(way.substring(way.indexOf("subnode=!#")+10));
			}
		}
		
		
		//Getting the distance between keypoints and motorway entry nodes
		for(String keyPoint : input){
			String id = "", minLat = "", minLon ="";
			double distance = -1;
			for(String portalPoint : layerPortals){
				String[] keyPointData = keyPoint.split(",");
				String[] portalPointData = portalPoint.split(",");
				
				if(distance>=0){
					if(distance>Math.sqrt(Math.pow(Double.parseDouble(keyPointData[2])-Double.parseDouble(portalPointData[2]), 2)+Math.pow(Double.parseDouble(keyPointData[1])-Double.parseDouble(portalPointData[1]), 2))){
						distance = Math.sqrt(Math.pow(Double.parseDouble(keyPointData[2])-Double.parseDouble(portalPointData[2]), 2)+Math.pow(Double.parseDouble(keyPointData[1])-Double.parseDouble(portalPointData[1]), 2));
						minLon = portalPointData[2];
						minLat = portalPointData[1];
						id = portalPointData[0].substring(1);
					}
				}
				else{
					distance = Math.sqrt(Math.pow(Double.parseDouble(keyPointData[2])-Double.parseDouble(portalPointData[2]), 2)+Math.pow(Double.parseDouble(keyPointData[1])-Double.parseDouble(portalPointData[1]), 2));
					minLon = portalPointData[2];
					minLat = portalPointData[1];
					id = portalPointData[0].substring(1);
				}
				
			}
			subpaths.add(keyPoint+"->"+id+","+minLat+","+minLon);
		}
		return subpaths;
	}
	
	public String[] closestNodes(String[] nodes, JALDocument document) throws FileNotFoundException, XMLStreamException{
		String closestNodes[] = new String[2];
		double minLon = -1, minLat = -1, maxLon = -1, maxLat = -1;
		String startId = "", endId = "";
		
			ArrayList<String> elements = document.getElementsDataByType("way");
			for(String element : elements){
				String[] components = element.split("::");
				for(String component : components){
					if(component.startsWith("subnode=")){
						if(minLon>=0&&minLat>=0){
							if(Math.sqrt((minLon-Double.parseDouble(nodes[0].split(",")[2]))*(minLon-Double.parseDouble(nodes[0].split(",")[2]))+(minLat-Double.parseDouble(nodes[0].split(",")[1]))*(minLat-Double.parseDouble(nodes[0].split(",")[1])))>Math.sqrt((Double.parseDouble(component.substring(8).split(",")[2])-Double.parseDouble(nodes[0].split(",")[2]))*(Double.parseDouble(component.substring(8).split(",")[2])-Double.parseDouble(nodes[0].split(",")[2]))+(Double.parseDouble(component.substring(8).split(",")[1])-Double.parseDouble(nodes[0].split(",")[1]))*(Double.parseDouble(component.substring(8).split(",")[1])-Double.parseDouble(nodes[0].split(",")[1])))){
								minLon = Double.parseDouble(component.substring(8).split(",")[2]);
								minLat = Double.parseDouble(component.substring(8).split(",")[1]);
								startId = component.substring(8,component.indexOf(","));
							}
						}
						else{
							minLon = Double.parseDouble(component.substring(8).split(",")[2]);
							minLat = Double.parseDouble(component.substring(8).split(",")[1]);
							startId = component.substring(8,component.indexOf(","));
						}
						if(maxLon>=0&&maxLat>=0){
							if(Math.sqrt((maxLon-Double.parseDouble(nodes[1].split(",")[2]))*(maxLon-Double.parseDouble(nodes[1].split(",")[2]))+(maxLat-Double.parseDouble(nodes[1].split(",")[1]))*(maxLat-Double.parseDouble(nodes[1].split(",")[1])))>Math.sqrt((Double.parseDouble(component.substring(8).split(",")[2])-Double.parseDouble(nodes[1].split(",")[2]))*(Double.parseDouble(component.substring(8).split(",")[2])-Double.parseDouble(nodes[1].split(",")[2]))+(Double.parseDouble(component.substring(8).split(",")[1])-Double.parseDouble(nodes[1].split(",")[1]))*(Double.parseDouble(component.substring(8).split(",")[1])-Double.parseDouble(nodes[1].split(",")[1])))){
								maxLon = Double.parseDouble(component.substring(8).split(",")[2]);
								maxLat = Double.parseDouble(component.substring(8).split(",")[1]);
								endId = component.substring(8,component.indexOf(","));
							}
						}
						else{
							maxLon = Double.parseDouble(component.substring(8).split(",")[2]);
							maxLat = Double.parseDouble(component.substring(8).split(",")[1]);
							endId = component.substring(8,component.indexOf(","));
						}
					}
				}
			}
		
		closestNodes[0] = startId+","+minLat+","+minLon;
		closestNodes[1] = endId+","+maxLat+","+maxLon;
		return closestNodes;
	}
}
