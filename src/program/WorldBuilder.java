package program;


import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import exception.NoArgumentException;
import exception.WrongFormatException;
import parsing.JALBuilder;
import xml.OSMCompressor;


public class WorldBuilder {
	public static void main(String[] args) throws NoArgumentException {
		if(args.length==0) throw new NoArgumentException();
		try {
			/*File OSMFile = new File("src/digest/"+args[0]);
			OSMCompressor compressor = new OSMCompressor(OSMFile);
			compressor.compress();
			compressor.close();
			OSMFile.delete();*/
			
			
			File COSMFile = new File("data/digest/"+args[0].substring(0,args[0].length()-4)+".cosm");
			JALBuilder builder = new JALBuilder(COSMFile);
			//builder.buildFile();
			//COSMFile.delete();
			//builder.refine();
			builder.generateRelations();
			builder.generateSuggestions();
			builder.generateTourism();
			
			
		} 
		catch(IOException e){
			System.err.println(e.getMessage());
		} 
		catch (WrongFormatException e){
			System.err.println(e.getMessage());
		}
		catch (XMLStreamException e){
			System.err.println(e.getMessage());
		}
	}
}
