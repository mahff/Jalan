package parsing;

public class SubnodeInfo {
	private String before;
	private String after;

	public SubnodeInfo(String data, String id){
		String[] components = data.split("::");
		for(int i=0;i<components.length;i++){
			if(components[i].startsWith("subnode="+id+",")){
				if(components[i-1]!=null)
					if(components[i-1].startsWith("subnode="))
							before = "::"+components[i-1].substring(8);
				if(components.length>=i+2)
					if(components[i+1].startsWith("subnode="))
						after = "::"+components[i+1].substring(8);
			}
		}
	}
	public String getBefore(){
		return before;
	}
	public String getAfter(){
		
		return after;
	}
	public String toString(){
		if(before==null&&after==null)
			return "";
		else if(before==null)
			return after;
		else if(after==null)
			return before;
		return before+after;
	}
}
