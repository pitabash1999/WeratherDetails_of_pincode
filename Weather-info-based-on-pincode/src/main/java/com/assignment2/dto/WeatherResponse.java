package com.assignment2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {
    private Coord coord;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Sys sys;
    private String name;
    private int timezone;
    private int id;
    private int cod;
    private long dt;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }



    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        private double humidity;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Wind {
        private double speed;
        private int deg;
        private double gust;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Clouds {
        private int all;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {
        @JsonProperty("1h")
        private double oneHour;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;

    }
}














