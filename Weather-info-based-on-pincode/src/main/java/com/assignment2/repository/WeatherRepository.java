package com.assignment2.repository;

import com.assignment2.model.PincodeDetails;
import com.assignment2.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long> {

   Optional<Weather> findByPincodeAndDate(PincodeDetails pincode, LocalDate date);
}
