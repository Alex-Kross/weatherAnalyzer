package com.senla.testTask.weatherAnalyzer.controller;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.service.WeatherServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class WeatherController {
    private static final Logger LOGGER =
            Logger.getLogger(WeatherController.class.getName());
    private final WeatherServer weatherServer;

    public WeatherController(WeatherServer weatherServer) {
        this.weatherServer = weatherServer;
    }

    // I need handle exception that I throws and find best practice for integrate api
    // occasion: api not work, suddenly disable internet,  i get nothing from api
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
    public void saveWeather() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=Minsk"))
                .header("X-RapidAPI-Key", "9fa4cd710bmsh97a622ee278463dp1eb7d9jsnff47781e292d")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        String response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        LOGGER.info("Data of weather got from api");
        weatherServer.saveWeather(response);
    }

    @GetMapping("/current-weather")
    public WeatherResponse getCurrentWeather() {
        return weatherServer.getCurrentWeather();
    }
}
