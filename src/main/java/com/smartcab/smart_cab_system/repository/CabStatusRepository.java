package com.smartcab.smart_cab_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcab.smart_cab_system.model.CabStatus;

@Repository
public interface CabStatusRepository extends JpaRepository<CabStatus, Long> {
    List<CabStatus> findByRegion(String region);
}
