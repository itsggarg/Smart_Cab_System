package com.smartcab.smart_cab_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcab.smart_cab_system.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
}
