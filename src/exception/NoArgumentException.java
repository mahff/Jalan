package exception;

public class NoArgumentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoArgumentException(){
		
	}
	
	public String getMessage(){
		return "No file has been passed as argument";
	}

}
