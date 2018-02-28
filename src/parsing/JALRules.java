package parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xml.Node;

public class JALRules {
	
	public JALRules(){
		
	}
	
	public Node format(String data){
		String[] components = data.split("::");
		Node node = new Node("<node >\n\t</node>");
		
		Pattern address = Pattern.compile("addr:(.*?)=(.*?)");
    	
		for(String component : components){
			Matcher matcher = address.matcher(component);
			
			//bounds of the map
			if(component.startsWith("<")){
				node.rename("meta");
				node.addChild(new Node("<minlat>"+component.substring(1,component.indexOf("#"))+"</minlat>"));
				node.addChild(new Node("<minlon>"+component.substring(component.indexOf("#")+1,component.length()-1)+"</minlon>"));
			}
			
			//Types of jal Nodes
			if(component.equals("way"))
				node.rename("way");
			if(component.equals("relation"))
				node.rename("relation");
			if(component.equals("area=yes")||component.equals("natural=water"))
				node.rename("area");
			if(component.equals("type=route"))
				node.rename("route");
			if(component.equals("natural=coastline"))
				node.rename("coast");
			if(component.equals("place=island"))
				node.rename("island");
			
			//Attributes
			if(component.startsWith("id=")){
				node.addAttribute("id",component.substring(3));
			}
			
			//Miscellaneous children
			if(component.startsWith("lat="))
				node.addChild(new Node("<lat>"+component.substring(4)+"</lat>"));
			if(component.startsWith("lon="))
				node.addChild(new Node("<lon>"+component.substring(4)+"</lon>"));
			if(component.startsWith("name="))
				node.addChild(new Node("<name>"+component.substring(5)+"</name>"));
			if(component.startsWith("ref="))
				node.addChild(new Node("<ref>"+component.substring(4)+"</ref>"));
			if(component.startsWith("link="))
				node.addChild(new Node("<link>"+component.substring(5)+"</link>"));	//References for transports
			if((component.equals("amenity=food_court")||component.equals("amenity=restaurant"))||(component.equals("amenity=fast_food")))
				node.addChild(new Node("<food>restaurant</food>"));
			if(component.equals("amenity=pub")||component.equals("amenity=bar"))
				node.addChild(new Node("<food>bar</food>"));
			if(component.equals("amenity=ice_cream"))
				node.addChild(new Node("<food>ice cream</food>"));
			if(component.equals("amenity=cafe"))
				node.addChild(new Node("<food>cafe</food>"));
			if(component.startsWith("colour="))
				node.addChild(new Node("<color>"+component.substring(7)+"</color>"));
			if((component.equals("highway=motorway")||component.equals("highway=motorway_link"))||(component.equals("highway=trunk")||component.equals("highway=trunk_link")))
				node.addChild(new Node("<road>motorway</road>"));
			if((component.equals("highway=primary")||component.equals("highway=primary_link"))||(component.equals("highway=secondary")||component.equals("highway=secondary_link")))
				node.addChild(new Node("<road>primary</road>"));
			if(component.equals("highway=tertiary")||component.equals("highway=tertiary_link"))
				node.addChild(new Node("<road>secondary</road>"));
			if((component.equals("highway=residential")||component.equals("highway=service"))||(component.equals("highway=unclassified")||component.equals("highway=road")))
				node.addChild(new Node("<road>tertiary</road>"));
			if(component.startsWith("layer="))
				node.addChild(new Node("<layer>"+component.substring(6)+"</layer>"));
			if(component.equals("historic=ruins")||component.equals("historic=archaeological_site"))
				node.addChild(new Node("<tourism>ruins</tourism>"));
			if(component.equals("tourism=museum")||component.equals("tourism=gallery"))
				node.addChild(new Node("<tourism>museum</tourism>"));
			if(component.equals("tourism=attraction"))
				node.addChild(new Node("<tourism>attraction</tourism>"));
			if(component.equals("tourism=zoo"))
				node.addChild(new Node("<tourism>zoo</tourism>"));
			if(component.equals("historic=castle"))
				node.addChild(new Node("<tourism>castle</tourism>"));
			if(component.equals("historic=monument"))
				node.addChild(new Node("<tourism>monument</tourism>"));
			if(component.equals("amenity=clinic")||component.equals("amenity=hospital"))
				node.addChild(new Node("<health>hospital</health>"));
			if(component.equals("amenity=pharmacy"))
				node.addChild(new Node("<health>pharmacy</health>"));
			if(component.equals("amenity=toilets"))
				node.addChild(new Node("<health>toilets</health>"));
			if(component.equals("waterway=river")||component.equals("waterway=riverbank"))
				node.addChild(new Node("<water>river</water>"));
			if(component.equals("waterway=stream"))
				node.addChild(new Node("<water>stream</water>"));
			
			//Simple children
			if(component.equals("amenity=post_office"))
				node.addChild(new Node("<postoffice/>"));
			if(component.equals("amenity=parking"))
				node.addChild(new Node("<parking/>"));
			if(component.equals("type=multipolygon")){
				node.addChild(new Node("<multipolygon/>"));
				node.rename("area");
			}
			if(component.startsWith("busway=")||component.startsWith("bus_bay="))
				node.addChild(new Node("<busway/>"));
			if(component.equals("amenity=atm")||component.equals("amenity=bureau_de_change"))
				node.addChild(new Node("<atm/>"));
			if(component.equals("junction=roundabout")||component.equals("junction=mini_roundabout"))
				node.addChild(new Node("<roundabout/>"));
			if((component.equals("tourism=hotel")||component.equals("tourism=motel"))||component.equals("tourism=hostel"))
				node.addChild(new Node("<hotel/>"));
			if((component.equals("shop=convenience")||component.equals("shop=mall"))||(component.equals("shop=department_store")||component.equals("shop=supermarket")))
				node.addChild(new Node("<commercial/>"));
			if((component.equals("highway=living_street")||component.equals("highway=pedestrian"))||((component.equals("highway=footway")||component.equals("highway=steps"))||(component.equals("highway=path")||component.equals("highway=cycleway"))))
				node.addChild(new Node("<pedestrian/>"));
			if(component.equals("highway=bus_stop"))
				node.addChild(new Node("<busstation/>"));
			if(component.equals("amenity=ferry_terminal"))
				node.addChild(new Node("<ferrystation/>"));
			if(component.equals("underground=station"))
				node.addChild(new Node("<railstation/>"));
			if(component.equals("natural=beach"))
				node.addChild(new Node("<beach/>"));
			if(component.equals("leisure=garden")||component.equals("leisure=park"))
				node.addChild(new Node("<park/>"));
			
			//Buildings
			if(component.startsWith("building=")){
				switch(component.substring(9)){
					case "commercial":
						node.addChild(new Node("<building>commercial</building>"));
					break;
					case "retail":
						node.addChild(new Node("<building>commercial</building>"));
					break;
					case "hotel":
						node.addChild(new Node("<building>hotel</building>"));
					break;
					case "kiosk":
						node.addChild(new Node("<building>commercial</building>"));
					break;
					case "stadium":
						node.addChild(new Node("<building>stadium</building>"));
					break;
					case "parking":
						node.addChild(new Node("<building>parking</building>"));
					break;
					case "train_station":
						node.addChild(new Node("<building>station</building>"));
					break;
					case "transportation":
						node.addChild(new Node("<building>station</building>"));
					break;
					case "hospital":
						node.addChild(new Node("<building>hospital</building>"));
					break;
					default: 
						node.addChild(new Node("<building>default</building>"));
				}
			}
			
			//Transport routes
			if(component.startsWith("route=")){
				switch(component.substring(6)){
					case "bus":
						node.addChild(new Node("<type>bus</type"));
					break;
					case "ferry":
						node.addChild(new Node("<type>ferry</type>"));
					break;
					case "tram":
						node.addChild(new Node("<type>rail</type>"));
					break;
					case "train":
						node.addChild(new Node("<type>rail</type>"));
					break;
					case "light_rail":
						node.addChild(new Node("<type>rail</type>"));
					break;
					case "road":
						node.addChild(new Node("<type>road</type>"));
					break;
					case "railway":
						node.addChild(new Node("<type>rail</type>"));
					break;
				}
			}
			
			//Rail stations
			if(component.startsWith("railway=")){
				switch(component.substring(8)){
					case "light_rail":
						node.addChild(new Node("<railway/>"));
					break;
					case "rail":
						node.addChild(new Node("<railway/>"));
					break;
					case "tram":
						node.addChild(new Node("<railway/>"));
					break;
					case "halt":
						node.addChild(new Node("<railstation/>"));
					break;
					case "tram_stop":
						node.addChild(new Node("<railstation/>"));
					break;
					case "station":
						node.addChild(new Node("<railstation/>"));
					break;
				}
			}
			
			//Land areas
			if(component.startsWith("landuse=")) {
				switch(component.substring(8)) {
					case "commercial":
						node.addChild(new Node("<landuse>commercial</landuse>"));
					break;
					case "retail":
						node.addChild(new Node("<landuse>commercial</landuse>"));
					break;
					case "basin":
						node.addChild(new Node("<landuse>water</landuse>"));
					break;
					case "grass":
						node.addChild(new Node("<landuse>park</landuse>"));
					break;
					case "recreation_ground":
						node.addChild(new Node("<landuse>park</landuse>"));
					break;
					case "forest":
						node.addChild(new Node("<landuse>forest</landuse>"));
					break;
				}
			}
			
			//Aeroways
			if(component.startsWith("aeroway=")) {
				switch(component.substring(8)) {
					case "taxiway":
						node.addChild(new Node("<airport>taxi</airport>"));
					break;
					case "taxilane":
						node.addChild(new Node("<airport>taxi</airport>"));
					break;
					case "runway":
						node.addChild(new Node("<airport>runway</airport>"));
					break;
					case "aerodrome":
						node.addChild(new Node("<airport>aerodrome</airport>"));
					break;
				}
			}
			
			//Subnodes
			if(component.startsWith("(subnode=")){
				String 	content[] = component.substring(9,component.length()-1).split(",");
				node.addChild(new Node("<subnode>"+content[0]+"</subnode>"));
			}
			if(component.startsWith("[subnode=")){
				String 	content[] = component.substring(9,component.length()-1).split(",");
				node.addChild(new Node("<subway>"+content[0]+"</subway>"));
			}
			
			//Child using regex -> Address elements
			if(matcher.find()){
				int i = 0;
				while(matcher.find()) {
					if(i==0){
						node.addChild(new Node("<"+matcher.group(1)+"></"+matcher.group(1)+">"));
						i++;
					}
					else node.setContent(matcher.group(1));
				}
			}
			
		}
		return node;
	}
}
