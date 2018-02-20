package exception;

public class WrongFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	private String fileName;
	private String format;
	
	public WrongFormatException(String fileName, String format){
		this.fileName = fileName;
		this.format = format;
	}
	
	public String getMessage(){
		return fileName+" is not in "+format+" format";
	}

}