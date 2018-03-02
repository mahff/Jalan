package parsing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

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
	
	public String getElementDataByType(String type) throws XMLStreamException, FileNotFoundException{
		isTreating = true;
		int event;
		String result = "";
		
		while(stream.hasNext()){
			event = stream.next();
			switch(event){
				case XMLEvent.START_ELEMENT:
					if(stream.getLocalName()==type){
						while(((event = stream.next())!=XMLEvent.END_DOCUMENT)){
							
							switch(event){
								case XMLEvent.ATTRIBUTE:
									result += "id="+stream.getAttributeValue(0);
								case XMLEvent.END_ELEMENT:
									if(stream.getLocalName()==type)
										return result;
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
				break;
			}
		}
		this.reset();
		return result;
	}
	
	public String getElementDataById(String id) throws XMLStreamException, FileNotFoundException{
		isTreating = true;
		int event;
		
		while(stream.hasNext()){
			event = stream.next();
			String result = "";
			switch(event){
				case XMLEvent.START_ELEMENT:
					String name = stream.getLocalName();
					event = stream.next();
					
					if(event==XMLEvent.ATTRIBUTE){
						if(stream.getAttributeValue(0)==id){
							while(((event = stream.next())!=XMLEvent.END_DOCUMENT)){
								
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
			}
		}
		this.reset();
		return "";
	}
	
	public void reset() throws XMLStreamException, FileNotFoundException{
		isTreating = false;
		stream.close();
		stream = factory.createXMLStreamReader(new FileReader(file));
	}
	
	public ArrayList<String> getWaysContaining(String id) throws FileNotFoundException, XMLStreamException{
		isTreating = true;
		ArrayList<String> result = new ArrayList<String>();
		int event;
		String temporaryId = "0";
		
		while(stream.hasNext()){
			event = stream.next();
			switch(event){
				case XMLEvent.START_ELEMENT:
					if(stream.getLocalName()=="way"||stream.getLocalName()=="area"){
						while(((event = stream.next())!=XMLEvent.END_DOCUMENT)){
							switch(event){
								case XMLEvent.ATTRIBUTE:
									temporaryId = stream.getAttributeValue(0);
								break;
								case XMLEvent.START_ELEMENT:
									if(stream.getLocalName()=="subnode"){
										if(stream.getElementText()==id){
											result.add(temporaryId);
										}
									}
								break;
							}
						}
					}
				break;
			}
		}
		this.reset();
		return result;
	}
	
	public ArrayList<String> getAdjacentElements(String id) throws XMLStreamException, FileNotFoundException{
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> ways = this.getWaysContaining(id);
		
		for(String wayId : ways){
			String[] data = this.getElementDataById(wayId).split("::");
			for(int i=0;i<data.length;i++){
				if(data[i]==id){
					if(i-1!=-1) result.add(data[i-1]);
					if(i+1!=data.length) result.add(data[i+1]);
				}
			}
		}
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
