package parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class JALDocument {
	private XMLInputFactory factory = XMLInputFactory.newInstance();
	private XMLStreamReader stream;
	private String file;
	private boolean isTreating;
	
	public JALDocument(String file) throws FileNotFoundException, XMLStreamException{
		this.file = file;
		stream = factory.createXMLStreamReader(new FileReader(file));
	}
	
	public void close() throws XMLStreamException{
		stream.close();
	}
	
	public boolean hasEnded() {
		return isTreating;
	}
	
	public ArrayList<String> getElementsDataByType(String type) throws XMLStreamException, FileNotFoundException{
		if(isTreating == true) reset();
		isTreating = true;
		int event;
		ArrayList<String> result = new ArrayList<String>();
		result.add("");
		
		while(stream.hasNext()){
			event = stream.next();
			switch(event){
				case XMLEvent.START_ELEMENT:
					if(stream.getLocalName().equals(type)){
						
						int continueReading = 1;
						String data = "";
						
						if(stream.getAttributeValue(0)!=null) 
							data += "id="+stream.getAttributeValue(0);
						
						event = stream.next();
						if(event == XMLEvent.END_ELEMENT) 
							if(stream.getLocalName()==type)
								continueReading = 0;
						
						while(continueReading==1){
							switch(event){
								case XMLEvent.START_ELEMENT:
									data += "::"+stream.getLocalName();
									if(stream.next() == XMLEvent.CHARACTERS){
										data += "="+stream.getText();
									}
								break;
							}
							
							event = stream.next();
							if(event == XMLEvent.END_ELEMENT) 
								if(stream.getLocalName()==type)
									continueReading = 0;
						}
						result.add(data);
					}
				break;
			}
		}
		reset();
		return result;
	}
	
	public HashMap<String,String> getMappedElementsDataByType(String type) throws FileNotFoundException, XMLStreamException{
		ArrayList<String> elements = getElementsDataByType(type);
		HashMap<String,String> result = new HashMap<String,String>();
		
		for(String element : elements){
			if(!element.equals("")) {
				result.put(element.split("::")[0].substring(3), element.substring(element.indexOf("::")+2));
			}
		}
		return result;
	}
	
	public String getElementDataById(String id) throws XMLStreamException, FileNotFoundException{
		isTreating = true;
		int endedOnce = 0;
		int event;
		while(stream.hasNext()){
			event = stream.next();
			String result = "";
			switch(event){
				case XMLEvent.START_ELEMENT:
					String name = stream.getLocalName();

					if(stream.getAttributeValue(0)!=null){
						if(stream.getAttributeValue(0).equals(id)){
							while((event = stream.next()) != XMLEvent.END_DOCUMENT){
								switch(event){
									case XMLEvent.END_ELEMENT:
										if(stream.getLocalName()==name)
											return result.substring(2);
									break;
									case XMLEvent.START_ELEMENT:
										result += "::"+stream.getLocalName();
										if(!stream.isEndElement()){
											result += "="+stream.getElementText();
										}
									break;
								}
							}
						}
					}
				break;
				case XMLEvent.END_DOCUMENT:
					if(endedOnce == 0){
						reset();
						endedOnce = 1;
					}
				break;
			}
		}
		return "";
	}
	
	public void reset() throws XMLStreamException, FileNotFoundException{
		isTreating = false;
		stream.close();
		stream = factory.createXMLStreamReader(new FileReader(file));
	}
	
	public ArrayList<String> getWaysContaining(String id) throws FileNotFoundException, XMLStreamException{
		if(isTreating == true) reset();
		isTreating = true;
		ArrayList<String> result = new ArrayList<String>();
		int event;
		String temporaryId = "0";
		
		while(stream.hasNext()){
			event = stream.next();
			switch(event){
				case XMLEvent.START_ELEMENT:
					if(stream.getLocalName()=="way"){
						
						if(stream.getAttributeValue(0)!=null) 
							temporaryId = stream.getAttributeValue(0);
						
						int continueReading = 1;
						
						event = stream.next();
						if(event == XMLEvent.END_ELEMENT) 
							if(stream.getLocalName()=="way")
								continueReading = 0;
						
						while(continueReading==1){
							switch(event){
								case XMLEvent.START_ELEMENT:
									if(stream.getLocalName()=="subnode"){
										if((event = stream.next()) == XMLEvent.CHARACTERS){
											if(stream.getText().equals(id)){
												result.add(temporaryId);
											}
										}
									}
								break;
							}
							
							event = stream.next();
							if(event == XMLEvent.END_ELEMENT) 
								if(stream.getLocalName()=="way")
									continueReading = 0;
						}
					}
				break;
			}
		}
		reset();
		return result;
	}
	
	public HashMap<String,String> getMappedSubnodes() throws FileNotFoundException, XMLStreamException{
		HashMap<String,String> result = new HashMap<String,String>();
		ArrayList<String> subnodeContainers = getElementsDataByType("way");
		subnodeContainers.addAll(getElementsDataByType("area"));
		for(String subnodeContainer : subnodeContainers){
			for(String data : subnodeContainer.split("::")){
				if(data.startsWith("subnode=")){
					if(data.indexOf(",")!=-1){
						if(!result.containsKey(data.substring(8,data.indexOf(","))))
							result.put(data.substring(8,data.indexOf(",")), new SubnodeInfo(subnodeContainer,data.substring(8,data.indexOf(","))).toString());
						else result.put(data.substring(8,data.indexOf(",")),result.get(data.substring(8,data.indexOf(",")))+(new SubnodeInfo(subnodeContainer,data.substring(8,data.indexOf(",")))).toString());
					}
				}
			}
		}
		return result;
	}
	
	
	public ArrayList<String> getAdjacentElements(String id, String wayId) throws XMLStreamException, FileNotFoundException{
		ArrayList<String> result = new ArrayList<String>();
		//ArrayList<String> ways = this.getWaysContaining(id);
		
		//for(String wayId : ways){
			String[] data = this.getElementDataById(wayId).split("::");
			for(int i=0;i<data.length;i++){
				if(data[i].endsWith(id)){
					if(i-1!=-1) result.add(data[i-1].substring(data[i-1].indexOf("=")+1));
					if(i+1!=data.length) result.add(data[i+1].substring(data[i+1].indexOf("=")+1));
				}
			}
		//}
		return result;
	}
	
	public double[] getElementCoords(String id) throws FileNotFoundException, XMLStreamException{
		double[] result = new double[2];
		String[] data = getElementDataById(id).split("::");
		for(String component : data){
			if(component.startsWith("lon=")) result[1] = Double.parseDouble(component.substring(4));
			if(component.startsWith("lat=")) result[0] = Double.parseDouble(component.substring(4));
		}
		return result;
	}
	
	public boolean isWalkable(String id) throws FileNotFoundException, XMLStreamException{
		String[] data = getElementDataById(id).split("::");
		for(String component : data){
			if(component.equals("pedestrian")){
				return true;
			}
		}
		return false;
	}
}
