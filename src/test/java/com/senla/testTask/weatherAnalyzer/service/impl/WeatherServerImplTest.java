package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.mapper.impl.WeatherMapperImpl;
import org.junit.jupiter.api.Test;

class WeatherServerImplTest {

    @Test
    void saveWeather() {
        String apiResponse = "{\"location\":{\"name\":\"Minsk\",\"region\":\"Minsk\",\"country\":\"Belarus\",\n" +
                "\"lat\":53.9,\"lon\":27.57,\"tz_id\":\"Europe/Minsk\",\"localtime_epoch\":1702613437,\n" +
                "\"localtime\":\"2023-12-15 7:10\"},\"current\":{\"last_updated_epoch\":1702612800,\n" +
                "\"last_updated\":\"2023-12-15 07:00\",\"temp_c\":-2.0,\"temp_f\":28.4,\"is_day\":0,\n" +
                "\"condition\":{\"text\":\"Partly cloudy\",\n" +
                "\"icon\":\"//cdn.weatherapi.com/weather/64x64/night/116.png\",\"code\":1003},\n" +
                "\"wind_mph\":16.1,\"wind_kph\":25.9,\"wind_degree\":320,\"wind_dir\":\"NW\",\n" +
                "\"pressure_mb\":1014.0,\"pressure_in\":29.94,\"precip_mm\":0.02,\"precip_in\":0.0,\"humidity\":93,\n" +
                "\"cloud\":75,\"feelslike_c\":-8.2,\"feelslike_f\":17.3,\"vis_km\":10.0,\"vis_miles\":6.0,\"uv\":1.0,\n" +
                "\"gust_mph\":21.5,\"gust_kph\":34.6}}";
        Gson gson = new Gson();
        WeatherFromApi weatherFromApi = gson.fromJson(apiResponse, WeatherFromApi.class);
        WeatherMapperImpl weatherMapperImpl = new WeatherMapperImpl();
        Weather weather = weatherMapperImpl.toWeather(weatherFromApi);
    }
}