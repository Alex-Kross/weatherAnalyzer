package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.controller.WeatherController;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.mapper.impl.WeatherMapperImpl;
import com.senla.testTask.weatherAnalyzer.repository.WeatherRepository;
import com.senla.testTask.weatherAnalyzer.service.WeatherServer;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WeatherServerImpl implements WeatherServer {
    private final WeatherRepository weatherRepository;
    private static final Logger LOGGER =
            Logger.getLogger(WeatherController.class.getName());

    public WeatherServerImpl(WeatherRepository weatherRepository) {
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
        LOGGER.info("Data of weather saved. Data : " + weather);
    }
}
