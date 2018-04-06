package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchSuggestionParse {

	static ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> names = new ArrayList<String>();

	public static void readSug() {

		try {
			BufferedReader in = new BufferedReader(new FileReader("data/indexation/singapore.sug"));
			String str;
			while ((str = in.readLine()) != null) {
				data.add(str);
				System.out.println(str); 
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void matching(String searchLocation, int index) {
		for(String temp : data) {
			if(temp.contains(searchLocation)) {
				String[] name = temp.split("::"); 
				switch(index) {
				case 1 : ;
				}
				
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		readSug(); 
	}
}
