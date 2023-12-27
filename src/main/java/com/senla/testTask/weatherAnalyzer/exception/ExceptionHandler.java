package com.senla.testTask.weatherAnalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<Object> handleWeatherException(WeatherNotFoundException e){
        ResponseException responseException = new ResponseException(
                e.getMessage(),
                e.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RequestWrongException.class)
    public ResponseEntity<Object> handleRequestException(RequestWrongException e) {
        ResponseException responseException = new ResponseException(
                e.getMessage(),
                e.getCause(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiConnectException.class)
    public ResponseEntity<Object> handleApiConnectException(ApiConnectException e) {
        ResponseException responseException = new ResponseException(
                e.getMessage(),
                e.getCause(),
                HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<>(responseException, HttpStatus.SERVICE_UNAVAILABLE);
    }
}

