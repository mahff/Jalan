package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVDocument{
	private HashMap<String,String> nodes = new HashMap<String,String>();
	
	public CSVDocument(String file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while((line = reader.readLine()) != null){
			nodes.put(line.substring(0,line.indexOf("::")),line.substring(line.indexOf("::")+2));
		}
		reader.close();
	}
	
	public HashMap<String,String> getNodes(){
		return nodes;
	}
}
