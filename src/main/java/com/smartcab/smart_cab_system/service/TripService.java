package com.smartcab.smart_cab_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcab.smart_cab_system.model.Trip;
import com.smartcab.smart_cab_system.repository.TripRepository;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(int id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public void updateTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public void deleteTrip(int id) {
        tripRepository.deleteById(id);
    }
}
