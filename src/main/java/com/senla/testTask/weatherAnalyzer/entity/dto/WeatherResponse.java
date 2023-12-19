package com.senla.testTask.weatherAnalyzer.entity.dto;

import java.util.Objects;

public class WeatherResponse {
    private float temperature;
    private float windSpeed;
    private float pressure;
    private int humidity;
    private String weatherCondition;
    private String city;

    public WeatherResponse() {
    }

    public WeatherResponse(float temperature, float windSpeed, float pressure, int humidity, String weatherCondition, String city) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherCondition = weatherCondition;
        this.city = city;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherResponse that = (WeatherResponse) o;
        return Float.compare(temperature, that.temperature) == 0
                && Float.compare(windSpeed, that.windSpeed) == 0
                && Float.compare(pressure, that.pressure) == 0
                && humidity == that.humidity
                && Objects.equals(weatherCondition, that.weatherCondition)
                && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, windSpeed, pressure, humidity, weatherCondition, city);
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
