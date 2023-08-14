package br.com.bbnsdevelop.productservice.exceptions;

public class NotHasInventoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public NotHasInventoryException(String msg) {
		super(msg);
	}

}
