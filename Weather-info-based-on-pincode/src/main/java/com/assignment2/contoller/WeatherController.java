package com.assignment2.contoller;

import com.assignment2.dto.WeatherRequest;
import com.assignment2.dto.WeatherResponseDto;
import com.assignment2.service.interfaces.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/api/weather")
    public ResponseEntity<?> getWeather(@RequestBody WeatherRequest weatherRequest,
                                        @RequestParam(required = false,defaultValue = "IN")String countryCode){

        try {

            WeatherResponseDto weatherResponseDto=weatherService.getWeatherDetails(weatherRequest,countryCode);
            return new ResponseEntity<>(weatherResponseDto, HttpStatus.OK);
        } catch (NumberFormatException | NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
