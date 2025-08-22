package com.assignment2.repository;

import com.assignment2.model.PincodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PincodeRepository extends JpaRepository<PincodeDetails,Long> {

    Optional<PincodeDetails> findByPincode(String pincode);
}
