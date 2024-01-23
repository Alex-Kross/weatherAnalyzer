package com.senla.testTask.weatherAnalyzer.service;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;

import java.util.Map;

public interface WeatherService {
    Weather saveWeather(WeatherFromApi weatherFromApi);

    WeatherResponse getCurrentWeather();
    Map<String, Integer> getAverageTemp(String dateFrom, String dateTo);
}
