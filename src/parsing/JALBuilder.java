package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

import exception.WrongFormatException;
import xml.Node;

public class JALBuilder{
	private BufferedReader reader;
	private BufferedReader refinedReader;
	private BufferedWriter writer;
	private BufferedWriter temporaryWriter;
	private String file;
	
	public JALBuilder(File file) throws IOException, WrongFormatException{
		if(file.getName().endsWith(".cosm")){
			this.file = file.getName();
			//reader = new BufferedReader(new FileReader(file));
			//writer = new BufferedWriter(new FileWriter(new File("data/maps/"+file.getName().substring(0,file.getName().length()-5)+".jal")));
			//temporaryWriter = new BufferedWriter(new FileWriter(new File("data/digest/"+file.getName().substring(0,file.getName().length()-5)+".tmp")));
			//refinedReader = new BufferedReader(new FileReader(new File("data/digest/"+file.getName().substring(0,file.getName().length()-5)+".tmp")));
		}
		else throw new WrongFormatException(file.getName(),"cosm");
	}
	
	public void buildFile() throws IOException{
		String line;
		JALRules rules = new JALRules();
		int i = 1;
		
		temporaryWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<jalan>\n");
		
		while((line = reader.readLine())!=null){
			if(!rules.format(line).equals(new Node("")))
			temporaryWriter.write("\t"+rules.format(line)+"\n");
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			System.out.println("Built Node Set n°"+(i++));
		}
		
		temporaryWriter.write("</jalan>");
		temporaryWriter.close();
		reader.close();
	}
	
	public void refine() throws IOException, XMLStreamException{
		String line, pendingTag = "";
		int keep = 0, node = 0;
		JALDocument document = new JALDocument("data/digest/"+file.substring(0,file.length()-5)+".tmp");
		Pattern id = Pattern.compile("<subnode>(.*?)</subnode>");
		ArrayList<String> motorwayNodes = new ArrayList<String>();
		ArrayList<String> regularWayNodes = new ArrayList<String>(); 
		HashMap<String,String> nodes = document.getMappedElementsDataByType("node");
		ArrayList<String> ways = document.getElementsDataByType("way");
		
		//Adding node info to subnode tags and a portal marker on nodes that are part of both motorways and regular ways
		for(String way : ways){
			if(way.indexOf("road=motorway")!=-1){
				String[] data = way.split("::");
				for(String component : data){
					if(component.startsWith("subnode=")) 
						motorwayNodes.add(component.substring(8));
				}
			}
			else if(way.indexOf("road=primary")!=-1||way.indexOf("road=secondary")!=-1||way.indexOf("road=tertiary")!=-1){
				String[] data = way.split("::");
				for(String component : data){
					if(component.startsWith("subnode=")) 
						regularWayNodes.add(component.substring(8));
				}
			}
		}
		
		while((line = refinedReader.readLine()) != null){
			Matcher idMatcher = id.matcher(line);
			if(idMatcher.find()){
				String lon = "0.0", lat = "0.0";
				String identifier = idMatcher.group(1);
				//System.out.println("Reached "+idMatcher.group(1));
				if(nodes.get(identifier) != null){
					String[] data = nodes.get(identifier).split("::");
					for(String component : data){
						if(component.startsWith("lon=")) lon = component.substring(4);
						if(component.startsWith("lat=")) lat = component.substring(4);
					}
					//System.out.println(lat+","+lon);
					line = line.substring(0, line.indexOf(identifier)+identifier.length())+","+lat+","+lon+line.substring(line.indexOf(identifier)+identifier.length(),line.length());
					if(regularWayNodes.contains(identifier)&&motorwayNodes.contains(identifier))
						line = line.substring(0,line.indexOf(">")+1)+"!#"+line.substring(line.indexOf(">")+1,line.length());
				}
			}
			
			//Removing simple nodes
			if(line.trim().startsWith("<node")){
				node = 1;
				pendingTag += line+"\n";
			}
			if(node==0){
				writer.write(line+"\n");	
			}
			
			if(!line.trim().startsWith("<lon>")&&!line.trim().startsWith("<lat>")&&!line.trim().startsWith("<building>")&&!line.trim().startsWith("</node>")&&!line.trim().startsWith("<node")&&node==1){
				pendingTag += line+"\n";
				keep = 1;
			}
			if(line.trim().startsWith("<lon>")||line.trim().startsWith("<lat>")){
				pendingTag += line+"\n";
			}
			if(line.trim().startsWith("</node>")){
				if(keep==1) writer.write(pendingTag+"\n\t</node>\n");
				node = 0;
				keep = 0;
				pendingTag = "";
			}
		}
		refinedReader.close();
		writer.close();
		(new File(file.substring(0,file.length()-5)+".tmp")).delete();
	}
	
	public void generateRelations() throws XMLStreamException, IOException{
		System.out.println("Generating Realtions between nodes...(This may take quite a while)");
		ArrayList<String> treatedSubnodes = new ArrayList<String>();
		writer = new BufferedWriter(new FileWriter("data/indexation/"+file.substring(0,file.length()-5)+".rel"));
		JALDocument document = new JALDocument("data/maps/"+file.substring(0,file.length()-5)+".jal");
		
		HashMap<String,String> connections = document.getMappedSubnodes();
		HashMap<String, String> subways = document.getMappedElementsDataByType("way");
		
		for(String way : document.getElementsDataByType("way")){
			String car = "";
			if(way.indexOf("road=")!=-1)
				car = ",car";
			for(String component : way.split("::")){
				if(component.startsWith("subnode=")){
					if(!treatedSubnodes.contains(component.substring(8))){
						treatedSubnodes.add(component.substring(8));
						if(component.indexOf(",")!=-1){
							writer.write(component.substring(8));
							writer.write("::{walk,bicycle"+car+"}");
							if(connections.containsKey(component.substring(8,component.indexOf(","))))
								writer.write(connections.get(component.substring(8,component.indexOf(","))));
							writer.write("\n");
						}
					}
				}
			}
		}
		for(String area : document.getElementsDataByType("area")){
			if(area.indexOf("pedestrian")!=-1){
				for(String component : area.split("::")){
					if(component.startsWith("subnode=")){
						if(!treatedSubnodes.contains(component.substring(8))){
							treatedSubnodes.add(component.substring(8));
							if(component.indexOf(",")!=-1){
								writer.write(component.substring(8));
								writer.write("::{walk,bicycle}");
								if(connections.containsKey(component.substring(8,component.indexOf(","))))
									writer.write(connections.get(component.substring(8,component.indexOf(","))));
								writer.write("\n");
							}
						}
					}
				}
			}
		}
		for(String route : document.getElementsDataByType("route")){
			String bus = "", ferry = "", rail = "";
			if(route.indexOf("railway")!=-1){
				rail = "rail";
			}
			if(route.indexOf("busway")!=-1){
				bus = ",bus";
			}
			if(route.indexOf("ferryway")!=-1){
				ferry = ",ferry";
			}
			for(String component : route.split("::")){
				if(component.startsWith("subway=")){
					if(subways.get(component.substring(7))!=null){
						String way = subways.get(component.substring(7));
						for(String wayData : way.split("::")){
							if(wayData.startsWith("subnode=")){
								if(!treatedSubnodes.contains(component.substring(8))){
									treatedSubnodes.add(component.substring(8));
									if(component.indexOf(",")!=-1){
										writer.write(component.substring(8));
										writer.write("::{"+rail+bus+ferry+"}");
										if(connections.containsKey(component.substring(8,component.indexOf(","))))
											writer.write(connections.get(component.substring(8,component.indexOf(","))));
										writer.write("\n");
									}
								}
							}
						}
					}
				}
			}
		}
		
		writer.close();
	}
	
	public void generateTourism() throws XMLStreamException, IOException{
		System.out.println("Generating touristic heat map...");
		writer = new BufferedWriter(new FileWriter("data/indexation/"+file.substring(0,file.length()-5)+".trm"));
		JALDocument document = new JALDocument("data/maps/"+file.substring(0,file.length()-5)+".jal");
		ArrayList<String> meta = document.getElementsDataByType("meta");
		
		ArrayList<String> touristicPoints = new ArrayList<String>();
		
		for(String node : document.getElementsDataByType("node")){
			if(node.indexOf("tourism=")!=-1){
				if(node.substring(node.indexOf("lat=")+4).indexOf("::")!=-1) 
						touristicPoints.add(node.substring(node.indexOf("lat=")+4,node.substring(node.indexOf("lat=")+4).indexOf("::")+node.substring(0,node.indexOf("lat=")+4).length())+",");
				else touristicPoints.add(node.substring(node.indexOf("lat=")+4)+",");
				if(node.substring(node.indexOf("lon=")+4).indexOf("::")!=-1) 
					touristicPoints.add(node.substring(node.indexOf("lon=")+4,node.substring(node.indexOf("lon=")+4).indexOf("::")+node.substring(0,node.indexOf("lon=")+4).length())+"\n");
				else touristicPoints.add(node.substring(node.indexOf("lon=")+4)+"\n");
			}
		}
		
		for(String area : document.getElementsDataByType("area")){
			if(area.indexOf("tourism=")!=-1){
				if(area.substring(area.indexOf("name=")+5).indexOf("::")!=-1) 
					touristicPoints.add(area.substring(area.indexOf("name=")+5,area.substring(area.indexOf("name=")+5).indexOf("::")+area.substring(0,area.indexOf("name=")+5).length())+"::");
				else touristicPoints.add(area.substring(area.indexOf("name=")+5)+"::");
				if(area.substring(area.indexOf("subnode=")+8).indexOf("::")!=-1) 
						touristicPoints.add(area.substring(area.indexOf("subnode=")+8,area.substring(area.indexOf("subnode=")+8).indexOf("::")+area.substring(0,area.indexOf("subnode=")+8).length())+"\n");
				else touristicPoints.add(area.substring(area.indexOf("subnode=")+8)+"\n");
			}
		}
		
		for(String way : document.getElementsDataByType("way")){
			for(String data : way.split("::")){
				if(data.startsWith("subnode=")){
					int touristicCount = 0;
					for(String otherData : touristicPoints){
						if(data.split(",").length==3&&otherData.split(",").length==3){
							double lon1 = Double.parseDouble(data.split(",")[1])+Double.parseDouble(meta.get(1).split("::")[4].substring(7)), 
									lon2 = Double.parseDouble(otherData.split(",")[1])+Double.parseDouble(meta.get(1).split("::")[4].substring(7)), 
									lat1 = Double.parseDouble(data.split(",")[2])+Double.parseDouble(meta.get(1).split("::")[3].substring(7)), 
									lat2 = Double.parseDouble(otherData.split(",")[2])+Double.parseDouble(meta.get(1).split("::")[3].substring(7));
							
							double R = 6378.137;
							double dLongitude = lon2 * Math.PI / 180 - lon1 * Math.PI / 180,
									dLatitude = lat2* Math.PI / 180 - lat1 * Math.PI / 180;
							double rawDistance = Math.pow(Math.sin(dLatitude/2), 2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.pow(Math.sin(dLongitude/2),2);
							double refinedRawDistance = 2 * Math.atan2(Math.sqrt(rawDistance), Math.sqrt(1-rawDistance));
							double distance = 1000*R*refinedRawDistance;
							if(distance<500)
								touristicCount++;
						}
					}
					writer.write(data.substring(8)+"::"+touristicCount+"\n");
				}
			}
		}
		
		writer.close();
	}
	
	public void generateSuggestions() throws IOException, XMLStreamException{
		System.out.println("Generating Search suggestions...");
		writer = new BufferedWriter(new FileWriter("data/indexation/"+file.substring(0,file.length()-5)+".sug"));
		JALDocument document = new JALDocument("data/maps/"+file.substring(0,file.length()-5)+".jal");
		ArrayList<String> nodes = document.getElementsDataByType("node");
		ArrayList<String> ways = document.getElementsDataByType("way");
		ArrayList<String> areas = document.getElementsDataByType("area");
		
		for(String node : nodes){
			if(node.indexOf("name=")!=-1){
				if(node.substring(node.indexOf("name=")+5).indexOf("::")!=-1) 
					writer.write(node.substring(node.indexOf("name=")+5,node.substring(node.indexOf("name=")+5).indexOf("::")+node.substring(0,node.indexOf("name=")+5).length())+"::");
				else writer.write(node.substring(node.indexOf("name=")+5)+"::");
				if(node.substring(node.indexOf("lat=")+4).indexOf("::")!=-1) 
						writer.write(node.substring(node.indexOf("lat=")+4,node.substring(node.indexOf("lat=")+4).indexOf("::")+node.substring(0,node.indexOf("lat=")+4).length())+",");
				else writer.write(node.substring(node.indexOf("lat=")+4)+",");
				if(node.substring(node.indexOf("lon=")+4).indexOf("::")!=-1) 
					writer.write(node.substring(node.indexOf("lon=")+4,node.substring(node.indexOf("lon=")+4).indexOf("::")+node.substring(0,node.indexOf("lon=")+4).length())+"\n");
				else writer.write(node.substring(node.indexOf("lon=")+4)+"\n");
			}
		}
		
		for(String way : ways){
			if(way.indexOf("name=")!=-1&&way.indexOf("subnode")!=-1){
				if(way.substring(way.indexOf("name=")+5).indexOf("::")!=-1) 
					writer.write(way.substring(way.indexOf("name=")+5,way.substring(way.indexOf("name=")+5).indexOf("::")+way.substring(0,way.indexOf("name=")+5).length())+"::");
				else writer.write(way.substring(way.indexOf("name=")+5)+"::");
					if(way.substring(way.indexOf("subnode=")+8).indexOf("::")!=-1) 
						writer.write(way.substring(way.indexOf("subnode=")+8,way.substring(way.indexOf("subnode=")+8).indexOf("::")+way.substring(0,way.indexOf("subnode=")+8).length())+"\n");
					else writer.write(way.substring(way.indexOf("subnode=")+8)+"\n");
			}
		}
		
		for(String area : areas){
			if(area.indexOf("name=")!=-1){
				if(area.substring(area.indexOf("name=")+5).indexOf("::")!=-1) 
					writer.write(area.substring(area.indexOf("name=")+5,area.substring(area.indexOf("name=")+5).indexOf("::")+area.substring(0,area.indexOf("name=")+5).length())+"::");
				else writer.write(area.substring(area.indexOf("name=")+5)+"::");
				if(area.substring(area.indexOf("subnode=")+8).indexOf("::")!=-1) 
						writer.write(area.substring(area.indexOf("subnode=")+8,area.substring(area.indexOf("subnode=")+8).indexOf("::")+area.substring(0,area.indexOf("subnode=")+8).length())+"\n");
				else writer.write(area.substring(area.indexOf("subnode=")+8)+"\n");
			}
		}
		writer.close();
	}
}
