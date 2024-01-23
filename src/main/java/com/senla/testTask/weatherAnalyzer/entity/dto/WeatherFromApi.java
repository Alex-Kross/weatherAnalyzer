package com.senla.testTask.weatherAnalyzer.entity.dto;

import com.google.gson.annotations.SerializedName;

/***
 * This class contain data from weather API response.
 * It helps to retrieve data from JSON file to Object.
 */
public class WeatherFromApi {
    @SerializedName("location")
    private Location location;
    @SerializedName("current")
    private Current current;

    public WeatherFromApi() {
    }

    public WeatherFromApi(float temperature, float windSpeed, float pressure, int humidity,
                          String condition, String city, String dateTime) {
        this.location = new Location(city, dateTime);
        this.current = new Current(temperature, condition, windSpeed, pressure, humidity);
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public class Location{
        @SerializedName("name")
        private String city;
        @SerializedName("localtime")
        private String dateTime;

        public Location(String city, String dateTime) {
            this.city = city;
            this.dateTime = dateTime;
        }

        // get date from string contains date and time
        public String getDateTime() {
            return dateTime.split("\s")[0];
        }

        public String getCity() {
            return city;
        }
    }

    public class Current{
        @SerializedName("temp_c")
        private float temperature;
        @SerializedName("condition")
        private Condition condition;
        @SerializedName("wind_mph")
        private float windSpeed;
        @SerializedName("pressure_mb")
        private float pressure;
        @SerializedName("humidity")
        private int humidity;

        public Current(float temperature, String condition, float windSpeed, float pressure, int humidity) {
            this.temperature = temperature;
            this.condition = new Condition(condition);
            this.windSpeed = windSpeed;
            this.pressure = pressure;
            this.humidity = humidity;
        }

        public float getTemperature() {
            return temperature;
        }

        public Condition getCondition() {
            return condition;
        }

        public float getWindSpeed() {
            return windSpeed;
        }

        public float getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public class Condition{
        @SerializedName("text")
        private String condition;

        public Condition(String condition) {
            this.condition = condition;
        }

        public String getCondition() {
            return condition;
        }
    }
}
