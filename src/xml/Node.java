package xml;

import java.io.IOException;
import java.util.ArrayList;

public class Node{
	private String tag;
	
	public Node(String content){
		this.tag = content;
	}
	
	public ArrayList<Node> getChildren() throws IOException{
		ArrayList<Node> nodes = new ArrayList<Node>();
		String[] expressions = tag.split("\n");
		
		if(!this.isSimple()){
			for(int i=1;i<expressions.length-1;i++){
					nodes.add(new Node("<"+(expressions[i].split("<"))[1]));
			}
			return nodes;
		}
		return null;
	}
	
	public String getAttribute(String attribute){
		String head = (tag.split("\n"))[0];
			
		int beginPosition = head.indexOf(attribute+"=");
		int endPosition = head.substring(beginPosition+attribute.length()+2).indexOf("\"");
		if(beginPosition!=-1&&endPosition!=-1)
			return head.substring(beginPosition+attribute.length()+2,endPosition+head.substring(0,beginPosition+2+attribute.length()).length());
		else return null;
	}
	
	public String getContent() throws IOException{
		if(!(this.isSimple()||this.hasChildren())){
			return tag.substring(this.getName().length()+2,tag.length()-this.getName().length()-3);
		}
		return null;
	}
	
	public String getName(){
		return (tag.split(" "))[0].trim().substring(1);
	}
	
	public boolean isSimple(){
		return tag.trim().endsWith("/>");
	}
	
	public boolean hasChildren() throws IOException{
		return (this.getChildren().size() != 0);
	}
	
	public void addAttribute(String attribute, String value){
			int insertionPosition = tag.indexOf(" ");
			String startOfString = tag.substring(0,insertionPosition);
			String endOfString = tag.substring(insertionPosition,tag.length());
			tag = startOfString+" "+attribute+"=\""+value+"\""+endOfString;
	}
	
	public void addChild(Node child){
		int insertionPosition = tag.indexOf("\n");
		String startOfString = tag.substring(0,insertionPosition);
		String endOfString = tag.substring(insertionPosition,tag.length());
		tag = startOfString+"\n\t\t"+child.toString()+endOfString;
	}
	
	public void setContent(String content){
		if(!this.isSimple()){
			int insertionPosition = tag.indexOf(">");
			int endPosition = tag.substring(insertionPosition,tag.length()).indexOf("<")+tag.substring(0,insertionPosition).length();
			String startOfString = tag.substring(0,insertionPosition+1);
			String endOfString = tag.substring(endPosition,tag.length()-1);
			tag = startOfString+content+endOfString;
		}
	}
	
	public void rename(String newName){
		int cutPoint = tag.indexOf(" ");
		tag = "<"+newName+tag.substring(cutPoint,tag.length());
		cutPoint = tag.indexOf("</")+2;
		tag = tag.substring(0,cutPoint)+newName+">";
	}
	
	public String toString(){
		return tag;
	}
}
