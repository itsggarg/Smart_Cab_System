package com.smartcab.smart_cab_system.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartcab.smart_cab_system.model.CabDetail;
import com.smartcab.smart_cab_system.model.Trip;
import com.smartcab.smart_cab_system.service.CabService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private CabService cabService;

    @PostMapping("/allocate")
    public ResponseEntity<?> allocateCab(@RequestParam String region, @RequestParam String userLocation, @RequestBody Trip trip) {
        Optional<CabDetail> allocatedCab = cabService.allocateCab(region, userLocation, trip);

        if (allocatedCab.isPresent()) {
            return ResponseEntity.ok("Cab allocated successfully: " + allocatedCab.get());
        } else {
            return ResponseEntity.status(404).body("No available cabs in the region.");
        }
    }
}
