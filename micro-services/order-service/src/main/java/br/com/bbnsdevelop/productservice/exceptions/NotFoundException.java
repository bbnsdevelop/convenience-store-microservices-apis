package br.com.bbnsdevelop.productservice.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }
}
