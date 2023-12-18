package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.controller.WeatherController;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.entity.mapper.impl.WeatherMapperImpl;
import com.senla.testTask.weatherAnalyzer.repository.WeatherRepository;
import com.senla.testTask.weatherAnalyzer.service.WeatherService;
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
    private boolean isRightDateFormat(String dateFrom, String dateTo){
        String[] splitDateFrom = dateFrom.split("-");
        String[] splitDateTo = dateTo.split("-");
        //if client input date without day, month or year
        if (splitDateTo.length != 3
                || splitDateFrom.length != 3) {
            return false;
        }


    }
    //incorrect date format. right format here: dd-mm-yy.
    @Override
    public Map<String, Float> getAverageTemp(String dateFrom, String dateTo) {
        //check format parameters

        if (isRightDateFormat(dateFrom, dateTo)) {

        }
//        LocalDateTime.
        List<Weather> all =  weatherRepository.findAll();
        String lastDate = all.getLast().getDateTime().split("\s")[0];
        if (lastDate.compareTo(dateTo) < 0) {
            throw new RuntimeException("Your dateTo doesn't exist ");
        }
        List<String> date = new LinkedList<>();

        for (Weather weather :all) {
            date.add(weather.getDateTime().split("\s")[0]);
        }


        return new HashMap<>();
    }
}
