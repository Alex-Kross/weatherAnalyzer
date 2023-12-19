package com.senla.testTask.weatherAnalyzer.exception;

public class RequestWrongException extends RuntimeException{
    public RequestWrongException(String message) {
        super(message);
    }
}
