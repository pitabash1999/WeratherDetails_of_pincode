package com.assignment2.service.impls;

import com.assignment2.dto.PincodeResponse;
import com.assignment2.dto.WeatherRequest;
import com.assignment2.dto.WeatherResponse;
import com.assignment2.dto.WeatherResponseDto;
import com.assignment2.model.PincodeDetails;
import com.assignment2.model.Weather;
import com.assignment2.repository.PincodeRepository;
import com.assignment2.repository.WeatherRepository;
import com.assignment2.service.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;



import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Override
    public WeatherResponseDto getWeatherDetails(WeatherRequest weatherRequest, String countryCode) {

        String pincode = weatherRequest.getPincode();
        LocalDate date = weatherRequest.getForDate();

        if(pincode ==null || pincode.isEmpty() || date==null){
            throw new NullPointerException("Invalid pincode or date");
        }

        if(!isValidPincode(pincode)){
            throw new  NumberFormatException("Invalid Pincode");
        }

        try {

            // 1. Check DB for existing pincode
            PincodeDetails pincodeDetails = pincodeRepository.findByPincode(pincode).orElse(null);

            if (pincodeDetails == null) {
                String lonLatUrl = "http://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + "," + (countryCode == null ? "IN" : countryCode) + "&appid=" + apiKey;
                PincodeResponse pincodeResponse = getLocationResponse(lonLatUrl);

                pincodeDetails = new PincodeDetails();
                pincodeDetails.setPincode(pincode);
                pincodeDetails.setLongitude(pincodeResponse.getLon());
                pincodeDetails.setLatitude(pincodeResponse.getLat());
                pincodeDetails = pincodeRepository.save(pincodeDetails);
            }

            // 2. Check DB for existing weather info
            Optional<Weather> existingWeather = weatherRepository.findByPincodeAndDate(pincodeDetails, date);
            if (existingWeather.isPresent()) {
                return convertToDto(pincodeDetails, existingWeather.get());
            }

            // 3. Fetch weather from API if not present in DB
            String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + pincodeDetails.getLatitude() + "&lon=" + pincodeDetails.getLongitude() + "&appid=" + apiKey + "&units=metric";
            WeatherResponse weatherResponse = getWeatherResponse(weatherUrl);

            Weather weather = new Weather();
            weather.setDate(date);
            weather.setPincode(pincodeDetails);
            weather.setTemprature(weatherResponse.getMain().getTemp());
            weather.setHumidity(weatherResponse.getMain().getHumidity());
            weather.setWindSpeed(weatherResponse.getWind().getSpeed());
            weather.setCountryId(weatherResponse.getSys().getCountry());
            weather.setLocationName(weatherResponse.getName());

            Weather savedWeather = weatherRepository.save(weather);
            return convertToDto(pincodeDetails, savedWeather);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to connect to API" );
        }
    }

    //Checking if the pincode id valid
    private boolean isValidPincode(String pincode){

        try{

            Long.parseLong(pincode);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }

    //Fetching pincode location details
    private PincodeResponse getLocationResponse(String url) {

        try {
            return restTemplate.getForObject(url, PincodeResponse.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Fetching weather details
    private WeatherResponse getWeatherResponse(String url) {

        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Converting to appropriate response format
    private WeatherResponseDto convertToDto(PincodeDetails pincodeDetails, Weather weather) {
        WeatherResponseDto.PincodeDetail pincodeDetail = new WeatherResponseDto.PincodeDetail();
        pincodeDetail.setPincode(pincodeDetails.getPincode());
        pincodeDetail.setLat(pincodeDetails.getLatitude());
        pincodeDetail.setLon(pincodeDetails.getLongitude());

        WeatherResponseDto.WeatherDto weatherDto = new WeatherResponseDto.WeatherDto();
        weatherDto.setDate(weather.getDate());
        weatherDto.setHumidity(weather.getHumidity());
        weatherDto.setTemprature(weather.getTemprature());
        weatherDto.setWindSpeed(weather.getWindSpeed());
        weatherDto.setLocationName(weather.getLocationName());
        weatherDto.setCountryId(weather.getCountryId());

        WeatherResponseDto dto = new WeatherResponseDto();
        dto.setPincodeDetails(pincodeDetail);
        dto.setWeatherDetails(weatherDto);
        return dto;
    }
}
