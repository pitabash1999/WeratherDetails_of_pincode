package com.assignment2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherRequest {

    private String pincode;
    @JsonProperty("for_date")
    private LocalDate forDate;
}
