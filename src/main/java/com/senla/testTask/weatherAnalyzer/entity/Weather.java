package com.senla.testTask.weatherAnalyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Weather {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private float temperature;
    private float windSpeed;
    private float pressure;
    private int humidity;
    private String weatherCondition;
    private String city;
    private LocalDate localDate;

    public Weather() {
    }

    public Weather(float temperature, float windSpeed, float pressure,
                   int humidity, String weatherCondition, String city, LocalDate localDate) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherCondition = weatherCondition;
        this.city = city;
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Float.compare(temperature, weather.temperature) == 0 && Float.compare(windSpeed, weather.windSpeed) == 0 && Float.compare(pressure, weather.pressure) == 0 && humidity == weather.humidity && Objects.equals(id, weather.id) && Objects.equals(weatherCondition, weather.weatherCondition) && Objects.equals(city, weather.city) && Objects.equals(localDate, weather.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, windSpeed, pressure, humidity, weatherCondition, city, localDate);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", windSpeed=" + windSpeed +
                ", pressure=" + pressure +
                ", humidity2=" + humidity +
                ", condition2='" + weatherCondition + '\'' +
                ", city='" + city + '\'' +
                ", dateTime='" + localDate + '\'' +
                '}';
    }
}

