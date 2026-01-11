package com.smartcharge.station.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartcharge.station.service.StationService;
import com.smartcharge.station.service.StationService.ChargingStationWithDistance;


@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService service;

    public StationController(StationService service) {
        this.service = service;
    }

    @GetMapping("/nearby")
    public List<ChargingStationWithDistance> getNearbyStations(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return service.findStationsWithDistance(lat, lon);
    }
}