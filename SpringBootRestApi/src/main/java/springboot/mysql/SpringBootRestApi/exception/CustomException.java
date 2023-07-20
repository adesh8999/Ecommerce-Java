package springboot.mysql.SpringBootRestApi.exception;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public CustomException() {
		super();
	}

	public CustomException(String message) {
		super(message);
		this.message= message;
		
	}
	
	
	

}
