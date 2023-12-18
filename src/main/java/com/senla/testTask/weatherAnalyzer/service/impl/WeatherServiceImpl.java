package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.controller.WeatherController;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.entity.mapper.impl.WeatherMapperImpl;
import com.senla.testTask.weatherAnalyzer.repository.WeatherRepository;
import com.senla.testTask.weatherAnalyzer.service.WeatherService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

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

    //occasions: weather not found in list, not convert
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
        Weather weather =all.get(all.size() - 1);
        LOGGER.info("Last weather update found");

        WeatherResponse response = new WeatherMapperImpl().toResponse(weather);
        LOGGER.info("The found weather : \n" + weather);
        return response;
    }

    //incorrect date format. right format here: dd-mm-yy.
    @Override
    public Map<String, Integer> getAverageTemp(String dateFrom, String dateTo) {
        if (dateFrom == null && dateTo != null
                ||dateFrom != null && dateTo == null) {
            throw new RuntimeException("input only one parameter");
        }
        int averageTemp = 0;
        Map<String, Integer> avgTemp = new HashMap<>();
        avgTemp.put("average_temp", averageTemp);
        if (dateFrom == null && dateTo == null){
            averageTemp = getAverageTempToday();
        }
        //check format parameters
        else {
            averageTemp = getAverageTempRange(dateFrom, dateTo);
        }
        avgTemp.put("average_temp", averageTemp);
        return avgTemp;
    }

    private int getAverageTempToday(){
        List<Weather> weatherList =  weatherRepository.findAll();
        float sumTemperature = 0;
        for (Weather weather: weatherList) {
            sumTemperature += weather.getTemperature();
        }
        if (weatherList.isEmpty()) {
            throw new RuntimeException("list is empty");
        }
        double ceil = Math.ceil(sumTemperature / weatherList.size());
        return (int) ceil;
    }

    private int getAverageTempRange(String dateFrom, String dateTo){
        if (isRightDateFormat(dateFrom, dateTo)) {
            List<Weather> weatherList =  weatherRepository.findAll();
            //if date to is future date
            String lastRecordInDB = weatherList.get(weatherList.size() - 1).getDateTime().split("\s")[0];
            if (dateTo.compareTo(lastRecordInDB) > 0) {
                throw new RuntimeException("dateTo too much");
            }

            float sumTemperature = 0;
            int numberWeatherInRange = 0;
            for (Weather weather: weatherList) {
                String dateWeather = weather.getDateTime().split("\s")[0];
                if (dateFrom.compareTo(dateWeather) >= 0
                        && dateTo.compareTo(dateWeather) <= 0) {
                    sumTemperature += weather.getTemperature();
                    numberWeatherInRange++;
                }
            }
            if (numberWeatherInRange == 0) {
                new RuntimeException("noup value");
            }
            return (int) Math.ceil(sumTemperature / numberWeatherInRange);
        }
        return 0;
    }

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
        if(splitDateTo[0].length() != 4 || splitDateTo[1].length() != 2
                || splitDateTo[2].length() != 2 || splitDateFrom[0].length() != 4
                || splitDateFrom[1].length() != 2 || splitDateFrom[2].length() != 2){
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
