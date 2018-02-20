package program;


import java.io.File;
import java.io.IOException;

import exception.NoArgumentException;
import exception.WrongFormatException;
import xml.JALBuilder;
import xml.OSMCompressor;


public class WorldBuilder {
	public static void main(String[] args) throws NoArgumentException {
		if(args.length==0) throw new NoArgumentException();
		try {
			OSMCompressor compressor = new OSMCompressor(new File(args[0]));
			compressor.compress();
			compressor.close();
			JALBuilder builder = new JALBuilder(new File(args[0].substring(0,args[0].length()-4)+".cosm"));
			builder.buildFile();
			builder.close();
		} 
		catch(IOException e){
			System.err.println(e.getMessage());
		} 
		catch (WrongFormatException e){
			System.err.println(e.getMessage());
		}
	}
}
