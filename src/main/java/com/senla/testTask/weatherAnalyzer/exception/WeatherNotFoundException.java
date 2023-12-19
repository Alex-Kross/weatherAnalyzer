package com.senla.testTask.weatherAnalyzer.exception;

public class WeatherNotFoundException extends RuntimeException {

    public WeatherNotFoundException(String message) {
        super(message);
    }
}
