package com.senla.testTask.weatherAnalyzer.service;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;

public interface ApiService {
    WeatherFromApi getWeatherFromApi(String apiResponse);
}
