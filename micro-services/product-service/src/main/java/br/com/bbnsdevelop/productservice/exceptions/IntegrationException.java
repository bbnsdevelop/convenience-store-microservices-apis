package br.com.bbnsdevelop.productservice.exceptions;

public class IntegrationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public IntegrationException(String msg) {
		super(msg);
	}

}
