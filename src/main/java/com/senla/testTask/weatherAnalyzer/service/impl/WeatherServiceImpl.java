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

    //potentiall occasions are db not save, not convert to entity
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
        List<Weather> all = weatherRepository.findAll();
        if (all.isEmpty()) {
            throw new WeatherNotFoundException("Not found weather info!");
        }
        Weather weather = all.get(all.size() - 1);
        LOGGER.info("Last weather update found");

        WeatherResponse response = new WeatherMapperImpl().toResponse(weather);
        LOGGER.info("The found weather : \n" + weather);
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

        //if we don't have range then find average temp. for today
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
        List<Weather> weatherList =  weatherRepository.findAll();
        if (weatherList.isEmpty()) {
            throw new WeatherNotFoundException("Not found weather info!");
        }

        float sumTemperature = 0;
        for (Weather weather: weatherList) {
            sumTemperature += weather.getTemperature();
        }
        return (int) Math.ceil(sumTemperature / weatherList.size());
    }

    /***
     * This method calculate average temperature from "dateFrom" to "dateTo".
     * Firstly we check format request parameters and then find average temperature.
     *
     * @param dateFrom is date from client looking for average temperature
     * @param dateTo is date to client looking for average temperature
     * @return average temperature in range date
     */
    private int getAverageTempInRange(String dateFrom, String dateTo){
        if (isRightDateFormat(dateFrom, dateTo)) {
            List<Weather> weatherList = weatherRepository.findAll();
            if (weatherList.isEmpty()) {
                throw new WeatherNotFoundException("Not found weather info!");
            }
            //if date to is future date
            String lastRecordInDB = weatherList.get(weatherList.size() - 1).getDateTime().split("\s")[0];
            if (dateTo.compareTo(lastRecordInDB) > 0) {
                throw new RuntimeException("dateTo too much");
            }

            float sumTemperature = 0;
            int numberWeatherInRange = 0;
            for (Weather weather : weatherList) {
                String dateWeather = weather.getDateTime().split("\s")[0];
                if (dateFrom.compareTo(dateWeather) <= 0
                        && dateTo.compareTo(dateWeather) <= 0) {
                    sumTemperature += weather.getTemperature();
                    numberWeatherInRange++;
                }
            }
            // not found
            if (numberWeatherInRange == 0) {
                return 0;
            }
            return (int) Math.ceil(sumTemperature / numberWeatherInRange);
        } else {
            throw new RuntimeException("Incorrect input parameters");
        }
    }

    /***
     * Check request parameters right format. Right format: DD-MM-YY
     * @param dateFrom is date from client looking for average temperature
     * @param dateTo is date to client looking for average temperature
     * @return value that say as request parameters are right format
     */
    private boolean isRightDateFormat(String dateFrom, String dateTo){
        String[] splitDateFrom = dateFrom.split("-");
        String[] splitDateTo = dateTo.split("-");

        //if client input date without day, month or year
        // and if client input nothing
        if (splitDateTo.length != 3
                || splitDateFrom.length != 3) {
            return false;
        }
        //if client input date in wrong sequence
        if(splitDateTo[0].length() != 2 || splitDateFrom[0].length() != 2
                || splitDateTo[1].length() != 2 || splitDateFrom[1].length() != 2
                || splitDateTo[2].length() != 4 || splitDateFrom[2].length() != 4){
            return false;
        }
        //if client not exist day or month
        if (Integer.parseInt(splitDateTo[1]) < 1 && Integer.parseInt(splitDateTo[1]) > 12
                || Integer.parseInt(splitDateFrom[1]) < 1 && Integer.parseInt(splitDateFrom[1]) > 12
                || Integer.parseInt(splitDateTo[2]) < 1 && Integer.parseInt(splitDateTo[2]) > 31
                || Integer.parseInt(splitDateFrom[2]) < 1 && Integer.parseInt(splitDateFrom[2]) >  31
        ) {
            return false;
        }
        return true;
    }
}
