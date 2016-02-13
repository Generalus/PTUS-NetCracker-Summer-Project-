package org.aksoftware.edu.udm.performance.exceptions;


public class NeedMoreStringsException extends Exception{
    public NeedMoreStringsException(){
        super();
    }
    public NeedMoreStringsException(String message){
        super(message);
    }
}
