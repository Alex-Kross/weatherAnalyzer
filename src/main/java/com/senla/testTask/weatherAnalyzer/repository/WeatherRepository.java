package com.senla.testTask.weatherAnalyzer.repository;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    @Query("SELECT ROUND(AVG(temperature), 0) FROM Weather\n" +
            "WHERE localDate = :date")
    int getAverageTempToday(@Param("date") LocalDate localDate);

    @Query("SELECT ROUND(AVG(temperature), 0) FROM Weather\n" +
            "WHERE localDate BETWEEN :dateFrom AND :dateTo")
    int getAverageTempInRange(@Param("dateFrom")LocalDate dateFrom,
                              @Param("dateTo")LocalDate dateTo);

    @Query("SELECT w FROM Weather w\n" +
            "ORDER BY id DESC\n" +
            "LIMIT 1")
    Weather getLastWeather();
}
