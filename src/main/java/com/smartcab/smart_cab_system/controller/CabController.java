package com.smartcab.smart_cab_system.controller;

import com.smartcab.smart_cab_system.model.CabDetail;
import com.smartcab.smart_cab_system.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabs")
public class CabController {

    @Autowired
    private CabService cabService;

    // Endpoint to retrieve all available cabs in a given region
    @GetMapping("/available")
    public ResponseEntity<List<CabDetail>> getAvailableCabs(@RequestParam String region) {
        List<CabDetail> availableCabs = cabService.findAvailableCabs(region);
        if (availableCabs.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(availableCabs);
    }

    // Endpoint to update cab status (can be called manually for testing or status management)
    @PutMapping("/status/{cabId}")
    public ResponseEntity<?> updateCabStatus(@PathVariable Long cabId, @RequestParam String status) {
        boolean updated = cabService.updateCabStatus(cabId, status);
        if (updated) {
            return ResponseEntity.ok("Cab status updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Cab not found or unable to update status.");
        }
    }
}
