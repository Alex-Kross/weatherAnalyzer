package com.senla.testTask.weatherAnalyzer.repository;

import com.senla.testTask.weatherAnalyzer.entity.Weather;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WeatherRepositoryTest {
    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void WeatherRepository_SaveOne_ReturnSavedWeather() {

        //Arrange
        Weather weather = new Weather(22, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));

        //Act
        Weather save = weatherRepository.save(weather);

        //Assert
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    public void getLastWeather(){
        //Arrange
        Weather weather = new Weather(22, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather2 = new Weather(18, 15,
                        1005, 100, "Cloudy",
                        "Liverpool", LocalDate.parse("2023-12-31"));

        //Act
        weatherRepository.save(weather);
        weatherRepository.save(weather2);
        Weather lastWeather = weatherRepository.getLastWeather();

        //Assert
        Assertions.assertThat(lastWeather).isNotNull();
        Assertions.assertThat(lastWeather).isEqualTo(weather2);
        Assertions.assertThat(lastWeather).isNotEqualTo(weather);
    }

    @Test
    public void getAverageTempToday() {
        float temperature1 = 18.4f;
        float temperature2 = 22f;
        float temperature3 = 33.7f;
        float temperature4 = 30.9f;
        Weather weather1 = new Weather(temperature1, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather2 = new Weather(temperature2, 15,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather3 = new Weather(temperature3, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather4 = new Weather(temperature4, 15,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2024-01-02"));

        weatherRepository.save(weather1);
        weatherRepository.save(weather2);
        weatherRepository.save(weather3);
        weatherRepository.save(weather4);
        int expectedAverageTemp = Math.round(temperature4);
        int averageTempToday = weatherRepository.getAverageTempToday(LocalDate.parse("2024-01-02"));

        Assert.assertEquals(expectedAverageTemp, averageTempToday);
    }
    @Test
    public void getAverageTempInRange() {
        float temperature1 = 2;
        float temperature2 = 3;
        float temperature3 = 5;
        float temperature4 = 7;
        Weather weather1 = new Weather(temperature1, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-29"));
        Weather weather2 = new Weather(temperature2, 15,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather3 = new Weather(temperature3, 20,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-30"));
        Weather weather4 = new Weather(temperature4, 15,
                1005, 100, "Cloudy",
                "Liverpool", LocalDate.parse("2023-12-31"));

        weatherRepository.save(weather1);
        weatherRepository.save(weather2);
        weatherRepository.save(weather3);
        weatherRepository.save(weather4);
        float v = temperature2 + temperature3 + temperature4;
        float v1 = v / 3;
        int expectedAverageTemp = Math.round((temperature2 + temperature3 + temperature4) / 3);
        int averageTempInRange = weatherRepository.getAverageTempInRange(
                LocalDate.parse("2023-12-30"),
                LocalDate.parse("2023-12-31")
        );

        Assert.assertEquals(expectedAverageTemp, averageTempInRange);
    }
}
