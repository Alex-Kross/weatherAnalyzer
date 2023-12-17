package com.senla.testTask.weatherAnalyzer.entity.mapper;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;

public interface WeatherMapper {
    Weather toWeather(WeatherFromApi weather);
}
