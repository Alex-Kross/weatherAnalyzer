package com.senla.testTask.weatherAnalyzer.service.impl;

import com.google.gson.Gson;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.service.ApiService;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService {
    /***
     *
     * Method retrieve data from API and transfer to dto object via "gson" library,
     *
     * @param apiResponse string contain response api in JSON format
     * @return dto object
     */
    @Override
    public WeatherFromApi getWeatherFromApi(String apiResponse) {
        Gson gson = new Gson();
        return gson.fromJson(apiResponse, WeatherFromApi.class);
    }
}
