package com.senla.testTask.weatherAnalyzer.exception;

public class WeatherRepoEmptyException extends RuntimeException {

    public WeatherRepoEmptyException(String message) {
        super(message);
    }
}
