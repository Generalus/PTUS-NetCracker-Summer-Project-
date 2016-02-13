package org.aksoftware.edu.udm.exception;


public class BadAttributeTypeParametersException extends RuntimeException {
    public BadAttributeTypeParametersException(String message){
        super(message);
    }

    public BadAttributeTypeParametersException() {
        super();
    }
}