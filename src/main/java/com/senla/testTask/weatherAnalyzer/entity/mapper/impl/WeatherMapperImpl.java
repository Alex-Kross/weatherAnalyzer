package com.senla.testTask.weatherAnalyzer.entity.mapper.impl;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.entity.mapper.WeatherMapper;

/***
 * This class map different variant object "weather"
 */
public class WeatherMapperImpl implements WeatherMapper {
    /***
     * Mapping special object to entity
     *
     * @param weatherFromApi  special object for retrieve data from json string
     * @return entity "Weather"
     */
    @Override
    public Weather toWeather(WeatherFromApi weatherFromApi){
        return new Weather(
                        weatherFromApi.getCurrent().getTemperature(),
                        weatherFromApi.getCurrent().getWindSpeed(),
                        weatherFromApi.getCurrent().getPressure(),
                        weatherFromApi.getCurrent().getHumidity(),
                        weatherFromApi.getCurrent().getCondition().getCondition(),
                        weatherFromApi.getLocation().getCity(),
                        weatherFromApi.getLocation().getLocalDateTime()
                );
    }

    /***
     * Mapping object for response to client request
     *
     * @param weather entity "Weather"
     * @return object for client
     */
    @Override
    public WeatherResponse toResponse(Weather weather) {
        return new WeatherResponse(
                weather.getTemperature(),
                weather.getWindSpeed(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getWeatherCondition(),
                weather.getCity()
        );
    }
}
