package com.senla.testTask.weatherAnalyzer.service;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;

import java.util.Map;

public interface WeatherService {
    void saveWeather(String apiResponse);

    WeatherResponse getCurrentWeather();
    Map<String, Float> getAverageTemp(String dateFrom, String dateTo);
}
