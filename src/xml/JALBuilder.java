package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import exception.WrongFormatException;
import parsing.JALRules;

public class JALBuilder{
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public JALBuilder(File file) throws IOException, WrongFormatException{
		if(file.getName().endsWith(".cosm")){
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(new File(file.getName().substring(0,file.getName().length()-5)+".jal")));
		}
		else throw new WrongFormatException(file.getName(),"cosm");
	}
	
	public void close() throws IOException{
		reader.close();
		writer.close();
	}
	
	public void buildFile() throws IOException{
		String line;
		JALRules rules = new JALRules();
		int i = 1;
		
		writer.write("<?xml version=\"1.0\" charset=\"UTF-8\" ?>\n<jalan>\n");
		
		while((line = reader.readLine())!=null){
			writer.write("\t"+rules.format(line)+"\n");
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			System.out.println("Built Node Set n°"+(i++));
		}
		
		writer.write("</jalan>");
	}
}
