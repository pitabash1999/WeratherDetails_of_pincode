package com.assignment2.contoller;

import com.assignment2.dto.WeatherRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getWeatherTest() throws Exception {

        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.setPincode("756044");
        weatherRequest.setForDate(LocalDate.now());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weatherRequest))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pincodeDetails").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weatherDetails").exists());
    }

    @Test
    void getWeatherTest_nullPin() throws Exception {

        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.setPincode("");
        weatherRequest.setForDate(LocalDate.now());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/weather")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(weatherRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void getWeatherTest_nullArgument() throws Exception {

        WeatherRequest weatherRequest=new WeatherRequest();


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/weather")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(weatherRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}