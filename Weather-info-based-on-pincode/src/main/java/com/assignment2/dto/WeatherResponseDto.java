package com.assignment2.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherResponseDto {
    private PincodeDetail pincodeDetails;
    private WeatherDto weatherDetails;


    @Data
    public static class PincodeDetail{
        private String pincode;
        private double lon;
        private double lat;
    }
    @Data
    public static class WeatherDto{
        private Double temprature;
        private Double humidity;
        private Double windSpeed;
        private LocalDate date;
        private String countryId;
        private String locationName;
    }
}
