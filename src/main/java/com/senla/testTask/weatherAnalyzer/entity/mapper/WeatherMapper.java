package com.senla.testTask.weatherAnalyzer.entity.mapper;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;

public interface WeatherMapper {
    Weather toWeather(WeatherFromApi weather);
    WeatherResponse toResponse(Weather weather);
}
