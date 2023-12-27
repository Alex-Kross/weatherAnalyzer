package com.senla.testTask.weatherAnalyzer.controller;

import com.senla.testTask.weatherAnalyzer.entity.dto.WeatherResponse;
import com.senla.testTask.weatherAnalyzer.exception.ApiConnectException;
import com.senla.testTask.weatherAnalyzer.service.WeatherService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class WeatherController {
    private static final Logger LOGGER =
            Logger.getLogger(WeatherController.class.getName());
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /***
     * This request return info about weather from last record in db
     *
     * @return last info about weather in db
     */
    @GetMapping("/current-weather")
    public WeatherResponse getCurrentWeather() {
        return weatherService.getCurrentWeather();
    }

    @GetMapping("/average-temp")
    public Map<String, Integer> getAvgTemperatureToday(@RequestParam(required = false, name = "from") String dateFrom,
                                                                  @RequestParam(required = false, name = "to") String dateTo){
        return weatherService.getAverageTemp(dateFrom, dateTo);
    }
}
