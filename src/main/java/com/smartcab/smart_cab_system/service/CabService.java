package com.smartcab.smart_cab_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcab.smart_cab_system.model.CabDetail;
import com.smartcab.smart_cab_system.model.CabStatus;
import com.smartcab.smart_cab_system.model.Trip;
import com.smartcab.smart_cab_system.repository.CabDetailsRepository;
import com.smartcab.smart_cab_system.repository.CabStatusRepository;
import com.smartcab.smart_cab_system.repository.TripRepository;

@Service
public class CabService {

    @Autowired
    private CabStatusRepository cabStatusRepository;

    @Autowired
    private CabDetailsRepository cabDetailsRepository;

    @Autowired
    private TripRepository tripRepository;

    // Allocate a cab for a trip based on the region
    public Optional<CabDetail> allocateCab(String startRegion, String userLocation, Trip trip) {
        // Step 1: Find all available cabs in the region
        List<CabStatus> cabStatuses = cabStatusRepository.findByRegion(startRegion);

        // Step 2: Filter for available cabs only
        List<CabStatus> availableCabs = cabStatuses.stream()
                .filter(cabStatus -> cabStatus.getStatus().equals("available"))
                .toList();

        if (availableCabs.isEmpty()) {
            return Optional.empty(); // No available cabs in the region
        }

        // Step 3: (Optional) Find the closest cab based on userLocation (latitude,longitude)
        CabStatus selectedCabStatus = availableCabs.get(0); // For simplicity, pick the first available cab

        // Step 4: Update the selected cab's status to "in-use"
        selectedCabStatus.setStatus("in-use");
        cabStatusRepository.save(selectedCabStatus);

        // Step 5: Create and save the trip
        trip.setCabId(selectedCabStatus.getCabId());
        tripRepository.save(trip);

        // Step 6: Return the allocated cab details
        return cabDetailsRepository.findById(selectedCabStatus.getCabId());
    }

    // Method to find available cabs in a region (already discussed)
    public List<CabDetail> findAvailableCabs(String startRegion) {
        return cabStatusRepository.findByRegion(startRegion).stream()
                .filter(cabStatus -> cabStatus.getStatus().equals("available"))
                .map(cabStatus -> cabDetailsRepository.findById(cabStatus.getCabId()).get())
                .toList();
    }

    // Method to update a cab's status
    public boolean updateCabStatus(Long cabId, String status) {
        Optional<CabStatus> cabStatus = cabStatusRepository.findById(cabId);
        if (cabStatus.isPresent()) {
            CabStatus statusUpdate = cabStatus.get();
            statusUpdate.setStatus(status);
            cabStatusRepository.save(statusUpdate);
            return true;
        }
        return false;
    }
}
