package com.senla.testTask.weatherAnalyzer.controller;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherFromApi;
import com.senla.testTask.weatherAnalyzer.exception.ApiConnectException;
import com.senla.testTask.weatherAnalyzer.service.ApiService;
import com.senla.testTask.weatherAnalyzer.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

@Controller
public class ApiController {
    private static final Logger LOGGER =
            Logger.getLogger(WeatherController.class.getName());
    private final WeatherService weatherService;
    private final ApiService apiService;
    @Value("${rapidApi.weather.uri}")
    private String uri;
    @Value("${rapidApi.weather.xRapidApiKey}")
    private String xRapidApiKey;
    @Value("${rapidApi.weather.xRapidApiHost}")
    private String xRapidApiHost;

    public ApiController(WeatherService weatherService, ApiService apiService) {
        this.weatherService = weatherService;
        this.apiService = apiService;
    }

    /***
     * Here we connect external weather api and save information about weather in db on schedule.
     *
     * We request weather in city Minsk by default.
     * By default we request info from api every minute. Interval set as ISO format in properties file.
     * More info about ISO format here https://en.wikipedia.org/wiki/ISO_8601#Durations
     * External api here "https://rapidapi.com/weatherapi/api/weatherapi-com"
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Scheduled(fixedDelayString = "${interval}")
    @Async
    public void saveWeather() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("X-RapidAPI-Key", xRapidApiKey)
                .header("X-RapidAPI-Host", xRapidApiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        String response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new ApiConnectException("disconnect to api");
        }
        //save weather state in dto object
        WeatherFromApi weatherFromApi = apiService.getWeatherFromApi(response);
        LOGGER.info("Data of weather got from api");
        weatherService.saveWeather(weatherFromApi);
    }
}
