package com.assignment2.service.interfaces;

import com.assignment2.dto.WeatherRequest;
import com.assignment2.dto.WeatherResponseDto;

public interface WeatherService {

    public WeatherResponseDto getWeatherDetails(WeatherRequest weatherRequest,String countryCode);
}
