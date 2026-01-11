package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EVRoutePlanner;

import entity.ChargingStations;

@RestController
public class Controller {

    @Autowired
    private EVRoutePlanner evRoutePlanner;

 // ...existing code...
@GetMapping("/")
public ChargingStations hello(
    @RequestParam(value = "startLat", required = false, defaultValue = "49.41461") Double startLat,
    @RequestParam(value = "startLon", required = false, defaultValue = "8.681495") Double startLon,
    @RequestParam(value = "endLat", required = false, defaultValue = "49.420318") Double endLat,
    @RequestParam(value = "endLon", required = false, defaultValue = "8.687872") Double endLon,
    @RequestParam(value = "range", required = false, defaultValue = "1000") Integer range) {

    return evRoutePlanner.startRouting(startLat, startLon, endLat, endLon, range);
}
// ...existing code...
}
