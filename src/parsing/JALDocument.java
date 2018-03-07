package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

import exception.WrongFormatException;
import parsing.JALDocument;
import parsing.JALRules;

public class JALBuilder{
	private BufferedReader reader;
	private BufferedReader refinedReader;
	private BufferedWriter writer;
	private BufferedWriter temporaryWriter;
	private String file;
	
	public JALBuilder(File file) throws IOException, WrongFormatException{
		if(file.getName().endsWith(".cosm")){
			this.file = file.getName();
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(new File(file.getName().substring(0,file.getName().length()-5)+".jal")));
			temporaryWriter = new BufferedWriter(new FileWriter(new File(file.getName().substring(0,file.getName().length()-5)+".tmp")));
			refinedReader = new BufferedReader(new FileReader(new File(file.getName().substring(0,file.getName().length()-5)+".tmp")));
		}
		else throw new WrongFormatException(file.getName(),"cosm");
	}
	
	public void buildFile() throws IOException{
		String line;
		JALRules rules = new JALRules();
		int i = 1;
		
		temporaryWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<jalan>\n");
		
		while((line = reader.readLine())!=null){
			temporaryWriter.write("\t"+rules.format(line)+"\n");
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
			System.out.println("Built Node Set n°"+(i++));
		}
		
		temporaryWriter.write("</jalan>");
		temporaryWriter.close();
		reader.close();
	}
	
	public void refine() throws IOException, XMLStreamException{
		String line;
		JALDocument document = new JALDocument(file.substring(0,file.length()-5)+".tmp");
		Pattern id = Pattern.compile("<subnode>(.*?)</subnode>");
		
		while((line = refinedReader.readLine())!=null){
			Matcher matcher = id.matcher(line);
			if(matcher.find()){
					String identifier = matcher.group(1);
					System.out.println("Reached "+matcher.group(1));
					double[] data = document.getElementCoords(identifier);
					System.out.println(data[0]+","+data[1]);
					line = line.substring(0, line.indexOf(identifier)+identifier.length())+","+data[0]+","+data[1]+line.substring(line.indexOf(identifier)+identifier.length(),line.length());
			}
			writer.write(line+"\n");
		}
		refinedReader.close();
		writer.close();
	}
}
