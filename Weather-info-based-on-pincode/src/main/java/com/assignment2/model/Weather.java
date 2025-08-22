package com.assignment2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double temprature;
    private Double humidity;
    private Double windSpeed;
    private LocalDate date;
    private String countryId;
    private String locationName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pincode_id",nullable = false)
    private PincodeDetails pincode;

}
