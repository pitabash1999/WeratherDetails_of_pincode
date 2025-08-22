package com.assignment2.service.impls;

import com.assignment2.dto.WeatherRequest;
import com.assignment2.dto.WeatherResponseDto;
import com.assignment2.service.interfaces.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOError;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceImplTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeatherDetailsTest_nullPincode() {

        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.setPincode("");
        weatherRequest.setForDate(LocalDate.now());

        assertThrows(NullPointerException.class,()->{
            WeatherResponseDto weatherResponseDto=weatherService
                    .getWeatherDetails(weatherRequest,"IN");
        });
    }

    @Test
    void getWeatherDetailsTest_invalidPincode() {

        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.setPincode("kjhlk");
        weatherRequest.setForDate(LocalDate.now());

        assertThrows(NumberFormatException.class,()->{
            WeatherResponseDto weatherResponseDto=weatherService
                    .getWeatherDetails(weatherRequest,"IN");
        });
    }



    @Test
    void getWeatherDetailsTest() {

        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.setPincode("756044");
        weatherRequest.setForDate(LocalDate.now());
        WeatherResponseDto weatherResponseDto= weatherService
                .getWeatherDetails(weatherRequest,"IN");

        assertNotNull(weatherRequest);
    }

    @Test
    void getWeatherDetailsTest_null() {

        WeatherRequest weatherRequest=new WeatherRequest();

        assertThrows(NullPointerException.class,()->{
            WeatherResponseDto weatherResponseDto= weatherService
                    .getWeatherDetails(weatherRequest,"IN");
        });

    }
}