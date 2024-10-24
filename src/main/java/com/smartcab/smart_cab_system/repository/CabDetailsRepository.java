package com.smartcab.smart_cab_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcab.smart_cab_system.model.CabDetail;

@Repository
public interface CabDetailsRepository extends JpaRepository<CabDetail, Long> {
    Optional<CabDetail> findByVehicleNumber(String vehicleNumber);
}
