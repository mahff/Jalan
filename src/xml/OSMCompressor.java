package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import exception.WrongFormatException;

public class OSMCompressor{
	private BufferedReader reader;
	private BufferedWriter writer;
	private double minLatitude;
	private double minLongitude;
	
	public OSMCompressor(File file) throws IOException, WrongFormatException{
		if(file.getName().endsWith(".osm")){
			reader= new BufferedReader(new FileReader(file));
			writer= new BufferedWriter(new FileWriter(new File(file.getName().substring(0,file.getName().length()-4)+".cosm")));
		}
		else throw new WrongFormatException(file.getName(),"osm");
	}
	
	public void close() throws IOException{
		reader.close();
		writer.close();
	}
	
	public void compress() throws IOException{
		String expression;
		int k = 0;
		
		while((expression = reader.readLine())!=null){
			k++;
			String tag = expression.trim();
			
			if(tag.trim().startsWith("<bounds")){
				Node node = new Node(tag);
				minLatitude = Double.parseDouble(node.getAttribute("minlat"));
				minLongitude = Double.parseDouble(node.getAttribute("minlon"));
			}
			else if((tag.trim().startsWith("<node")||tag.trim().startsWith("<way"))||tag.trim().startsWith("<relation")){
				if(tag.endsWith("/>")){
					Node node = new Node(tag);
					if(node.getName().equals("node")){
						String data = node.getName()+"::";
						data += "id="+node.getAttribute("id")+"::";
						data += "lat="+(Double.parseDouble(node.getAttribute("lat"))-minLatitude)+"::";
						data += "lon="+(Double.parseDouble(node.getAttribute("lon"))-minLongitude);
						writer.write(data+"\n");
					}
				}
				else{
					expression = reader.readLine();
					k++;
					while(!expression.trim().contains("</")){
						tag += "\n"+expression;
						expression = reader.readLine();
						k++;
					}
					Node node = new Node(tag+"\n"+expression);
					String data = node.getName()+"::";
					data += "id="+node.getAttribute("id");
					if(node.getAttribute("lat")!=null)
						data += "::"+"lat="+(Double.parseDouble(node.getAttribute("lat"))-minLatitude)+"::";
					if(node.getAttribute("lon")!=null)
						data += "lon="+(Double.parseDouble(node.getAttribute("lon"))-minLongitude);
					for(Node child : node.getChildren()){
						if(child.getName().equals("member")||child.getName().equals("nd")){
							if(child.getAttribute("role")!=null&&child.getAttribute("type")!=null){
								if(child.getAttribute("type").equals("node"))
									data+="::(subnode="+child.getAttribute("ref")+","+child.getAttribute("role")+")";
								else data+="::[subnode="+child.getAttribute("ref")+","+child.getAttribute("role")+"]";
							}
							else{
								data+="::(subnode="+child.getAttribute("ref")+")";
							}
						}
						else if(child.getName().equals("tag")){
							if(!child.getAttribute("k").equals("ref"))
								data += "::"+child.getAttribute("k")+"="+child.getAttribute("v");
							else
								data += "::link="+child.getAttribute("v");
						}
					}
					writer.write(data+"\n");
				}
			}
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			System.out.println("Processing:"+k*100/4376861+"%");
		}
	}
}
