package com.senla.testTask.weatherAnalyzer.dto;

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

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public class Location{
        @SerializedName("name")
        private String city;
        @SerializedName("country")
        private String country;
        @SerializedName("localtime")
        private String localDateTime;

        public String getLocalDateTime() {
            return localDateTime;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
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

        public String getCondition() {
            return condition;
        }
    }
}
