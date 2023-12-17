package com.senla.testTask.weatherAnalyzer.mapper;

import com.senla.testTask.weatherAnalyzer.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;

public class WeatherMapper {

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
