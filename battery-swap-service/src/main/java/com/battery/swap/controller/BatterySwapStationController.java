package com.battery.swap.controller;

import com.battery.swap.entity.BatterySwapStation;
import com.battery.swap.service.BatterySwapStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class BatterySwapStationController {

    private final BatterySwapStationService stationService;

    // 🔍 Get nearby stations sorted by distance
    // Example: /stations/nearby?lat=12.97&lon=77.59
    @GetMapping("/nearby")
    public ResponseEntity<List<BatterySwapStation>> getNearbyStations(
            @RequestParam double lat,
            @RequestParam double lon) {
        List<BatterySwapStation> stations = stationService.getNearbyStations(lat, lon);
        return ResponseEntity.ok(stations);
    }

    // 🔋 Reserve one charged battery at a specific station
    @PostMapping("/reserve/{id}")
    public ResponseEntity<BatterySwapStation> reserveBattery(@PathVariable Long id) {
        BatterySwapStation updatedStation = stationService.reserveBattery(id);
        return ResponseEntity.ok(updatedStation);
    }
}
