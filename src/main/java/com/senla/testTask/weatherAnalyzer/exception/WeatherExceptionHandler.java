package com.senla.testTask.weatherAnalyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class WeatherExceptionHandler {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {WeatherRepoEmptyException.class})
    public ResponseEntity<Object> weatherExceptionHandler(WeatherRepoEmptyException e){
        WeatherException weatherException = new WeatherException(
                e.getMessage(),
                e.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(weatherException, HttpStatus.NOT_FOUND);
    }
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult()
//                .getFieldErrors()
//                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//        return errors;
//    }
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(PetNotFoundException.class)
//    public  Map<String, String> weatherRepoEmpty(weatherRepoEmptyException ex){
//        Map<String, String> error = new HashMap<>();
//        error.put("error :", ex.getMessage());
//        return error;
//    }
}

