package com.senla.testTask.weatherAnalyzer.repository;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
