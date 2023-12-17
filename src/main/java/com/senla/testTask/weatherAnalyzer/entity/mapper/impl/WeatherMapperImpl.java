package com.senla.testTask.weatherAnalyzer.entity.mapper.impl;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.mapper.WeatherMapper;

public class WeatherMapperImpl implements WeatherMapper {

    public Weather toWeather(WeatherFromApi weather){
        return new Weather(
                        weather.getCurrent().getTemperature(),
                        weather.getCurrent().getWindSpeed(),
                        weather.getCurrent().getPressure(),
                        weather.getCurrent().getHumidity(),
                        weather.getCurrent().getCondition().getCondition(),
                        weather.getLocation().getCity(),
                        weather.getLocation().getLocalDateTime()
                );
    }
}
