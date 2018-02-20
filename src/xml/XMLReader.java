package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLReader {
	private File file;
	private double minLatitude;
	private double minLongitude;
	
	public XMLReader(File file) {
		this.file=file;
	}
	
	public void exportBlocks(BufferedWriter writer) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder dBuilder = builderFactory.newDocumentBuilder(); 
		Document document = dBuilder.parse(file);
		document.normalize();
		XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
		
		NodeList rootNodeList = document.getElementsByTagName("osm");
		Node root = rootNodeList.item(0);
		minLatitude = Double.parseDouble(document.getElementsByTagName("bounds").item(0).getAttributes().getNamedItem("minlat").getTextContent());
		minLongitude = Double.parseDouble(document.getElementsByTagName("bounds").item(0).getAttributes().getNamedItem("minlon").getTextContent());
		System.out.println("Initialized File...\nMinimum Latitude:"+minLatitude+"\nMinmimum Longitude:"+minLongitude);

		NodeList nodes = (NodeList) xpath.evaluate("//node", root, XPathConstants.NODESET);
		System.out.println("Reached!");
		for(int i=0;i<nodes.getLength();i++){
			String data = "";
			Element node = (Element) nodes.item(i);
			data += node.getNodeName()+"->";
			data += "id="+node.getAttribute("id")+"::";
			data += "lon="+(Double.parseDouble(node.getAttribute("lon"))-minLongitude)+"::";
			data += "lat="+(Double.parseDouble(node.getAttribute("lat"))-minLatitude);
			NodeList children = (NodeList) xpath.evaluate("//tag", node, XPathConstants.NODESET);
			for(int j=0;i<children.getLength();i++){
				Element child = (Element) children.item(j);
				data += "::"+child.getAttribute("k")+"="+child.getAttribute("v");
			}
			writer.write(data+"\n");;
			System.out.println("Added Node...");
		}
		
		NodeList ways = (NodeList) xpath.evaluate("//way", root, XPathConstants.NODESET);
		for(int i=0;i<ways.getLength();i++){
			String data = "";
			Element way = (Element) ways.item(i);
			data += way.getNodeName()+"->";
			data += "id="+way.getAttribute("id")+"::";
			NodeList children = (NodeList) xpath.evaluate("node()", way, XPathConstants.NODESET);
			for(int j=0;i<children.getLength();i++){
				Element child = (Element) children.item(j);
				if(child.getNodeName() == "nd")
					data += "::(subnode="+child.getAttribute("ref")+")";
				else if((child.getAttribute("k")!=null)&&(child.getAttribute("v")!=null))
					data += "::"+child.getAttribute("k")+"="+child.getAttribute("v");
			}
			writer.write(data+"\n");
			System.out.println("Added way...");
		}
			
		NodeList relations = (NodeList) xpath.evaluate("//relation", root, XPathConstants.NODESET);
		for(int i=0;i<relations.getLength();i++){
			String data = "";
			Element relation = (Element) relations.item(i);
			data += relation.getNodeName()+"->";
			data += "id="+relation.getAttribute("id")+"::";
			NodeList children = (NodeList) xpath.evaluate("node()", relation, XPathConstants.NODESET);
			for(int j=0;i<children.getLength();i++){
				Element child = (Element) children.item(j);
				if((child.getNodeName() == "member")){
					if(child.getAttribute("type")=="way")
						data += "::[subnode="+child.getAttribute("ref")+","+child.getAttribute("role")+"]";
					else if(child.getAttribute("type")=="node")
						data += "::(subnode="+child.getAttribute("ref")+","+child.getAttribute("role")+")";
				}
				else if(children.item(i).getNodeName() == "nd")
					data += "::(subnode="+child.getAttribute("ref")+")";
				else if((child.getAttribute("k")!=null)&&(child.getAttribute("v")!=null))
					data += "::"+child.getAttribute("k")+"="+child.getAttribute("v");
			}
			writer.write(data+"\n");
			System.out.println("Added relation...");
		}
	}
	
}
