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

    /***
     * Mapping special object to entity
     * And reverse date before mapping
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
                        reverseString(weatherFromApi.getLocation().getDateTime())
                );
    }

    /***
     * Reverse only date in "dateTime" string
     *
     * @param dateTime string with date and time
     * @return string like this dd-mm-yy
     */
    private String reverseString(String dateTime){
        String[] dateAndTime = dateTime.split("\s");
        String[] date = dateAndTime[0].split("-");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date[2]);
        stringBuilder.append("-");
        stringBuilder.append(date[1]);
        stringBuilder.append("-");
        stringBuilder.append(date[0]);
        stringBuilder.append("\s");
        stringBuilder.append(dateAndTime[1]);
        return stringBuilder.toString();
    };
}
