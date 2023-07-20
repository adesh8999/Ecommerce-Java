package springboot.mysql.SpringBootRestApi.exception;

public class AlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String msg) {
		super(msg);
		this.message = msg;
	}
	
	
	
	

}
