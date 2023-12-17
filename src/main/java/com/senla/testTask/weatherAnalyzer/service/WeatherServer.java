package com.senla.testTask.weatherAnalyzer.service;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;

public interface WeatherServer {
    void saveWeather(String apiResponse);

    WeatherResponse getCurrentWeather();
}
