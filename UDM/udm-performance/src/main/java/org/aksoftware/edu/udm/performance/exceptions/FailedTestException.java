package org.aksoftware.edu.udm.performance.exceptions;

/*
 *  Вызывается, если во время теста получены ошибочные данные, либо серв с БД перестал отвечать
 */

public class FailedTestException extends Exception {
    public FailedTestException(){
        super();
    }
    public FailedTestException(String message){
        super(message);
    }
}
