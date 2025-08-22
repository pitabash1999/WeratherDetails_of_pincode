package com.assignment2.dto;

import lombok.Data;

@Data
public class PincodeResponse {

    private String zip;
    private String name;
    private Double lat;
    private Double lon;
    private String country;

}
