package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.controller.WeatherController;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.entity.mapper.impl.WeatherMapperImpl;
import com.senla.testTask.weatherAnalyzer.exception.RequestWrongException;
import com.senla.testTask.weatherAnalyzer.exception.WeatherNotFoundException;
import com.senla.testTask.weatherAnalyzer.repository.WeatherRepository;
import com.senla.testTask.weatherAnalyzer.service.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;
    private static final Logger LOGGER =
            Logger.getLogger(WeatherController.class.getName());

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    //potential occasions are db not save, not convert to entity
    /***
     * Save state weather in db.
     * Method retrieve data from method parameter to special object via "gson" library,
     * so then we transfer data from special object to entity and save it in db.
     *
     * @param apiResponse string contain response api in JSON format
     */
    @Override
    public void saveWeather(String apiResponse) {
        Gson gson = new Gson();
        WeatherFromApi weatherFromApi = gson.fromJson(apiResponse, WeatherFromApi.class);
        Weather weather = new WeatherMapperImpl().toWeather(weatherFromApi);

        weatherRepository.save(weather);
        LOGGER.info("Data of weather saved. Data : \n" + weather);
    }

    //occasions: weather not convert
    /***
     * This method return the last weather info from db
     * Before return we found it in db we mapped object "Weather" to client
     * view via converting to object "WeatherResponse" with fewer fields.
     *
     * @return last info about weather in db
     */
    @Override
    public WeatherResponse getCurrentWeather() {
        Weather lastWeather = weatherRepository.getLastWeather();
        if (lastWeather == null) {
            throw new WeatherNotFoundException("The Weather not found");
        }
        WeatherResponse response = new WeatherMapperImpl().toResponse(lastWeather);
        LOGGER.info("The found weather : \n" + lastWeather);
        return response;
    }

    /**
     * This method find average temperature in range that point out in method parameters
     * These parameters got from request
     *
     * @param dateFrom is date start to looking for average temperature
     * @param dateTo is date finish to client looking for average temperature
     * @return average temperature in range date
     */
    @Override
    public Map<String, Integer> getAverageTemp(String dateFrom, String dateTo) {
        //check exist both request parameters
        if (dateFrom == null && dateTo != null
                || dateFrom != null && dateTo == null) {
            throw new RequestWrongException("Input only one request parameter!");
        }
        int averageTemp = 0;
        Map<String, Integer> avgTemp = new HashMap<>();     // create object to response to the client
        avgTemp.put("average_temp", averageTemp);

        //if we don't have range then find average temperature for today
        if (dateFrom == null && dateTo == null){
            averageTemp = getAverageTempToday();
        }
        else {
            averageTemp = getAverageTempInRange(dateFrom, dateTo);
        }
        avgTemp.put("average_temp", averageTemp);
        return avgTemp;
    }

    /***
     * Method calculate average temperature today.
     * Firstly we get all weather's objects from db and then
     * find average temperature
     *
     * @return value average temperature
     */
    private int getAverageTempToday(){
        try {
            return (int) Math.ceil(weatherRepository.getAverageTempToday(LocalDate.now()));
        } catch (RuntimeException e) {
            throw new WeatherNotFoundException("The Weather not found");
        }
    }

    /***
     * This method calculate average temperature from "dateFromString" to "dateToString".
     * Firstly we check format request parameters and then find average temperature.
     *
     * @param dateFromString is date from client looking for average temperature
     * @param dateToString is date to client looking for average temperature
     * @return average temperature in range date
     */
    private int getAverageTempInRange(String dateFromString, String dateToString){
        LocalDate dateFrom, dateTo;
        try {
            dateFrom = isRightDateFormat(dateFromString);
            dateTo = isRightDateFormat(dateToString);
            return (int) Math.ceil(weatherRepository.getAverageTempInRange(
                    LocalDate.parse(dateFrom.format(DateTimeFormatter.ofPattern("uuuu-M-d"))),
                    LocalDate.parse(dateTo.format(DateTimeFormatter.ofPattern("uuuu-M-d")))));
        } catch (DateTimeParseException e) {
            String message = "";
            if (e.getCause() == null) {
                message = "Date need to be only in format DD-MM-YYYY";
            } else {
                message = e.getCause().getMessage();
            }
            throw new RequestWrongException(message);
        } catch (RuntimeException e) {
            throw new WeatherNotFoundException("The Weather not found");
        }
    }
    private LocalDate isRightDateFormat(String date){
        return LocalDate.parse(date,
                DateTimeFormatter.ofPattern("d-M-uuuu")
                        .withResolverStyle(ResolverStyle.STRICT)
        );
    }
}
