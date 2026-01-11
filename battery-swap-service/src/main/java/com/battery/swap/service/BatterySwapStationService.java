package com.battery.swap.service;

import com.battery.swap.entity.BatterySwapStation;
import com.battery.swap.repository.BatterySwapStationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatterySwapStationService {

    private final BatterySwapStationRepository stationRepository;

    // ✅ Find nearby stations sorted by distance (Haversine)
    public List<BatterySwapStation> getNearbyStations(double userLat, double userLon) {
        return stationRepository.findAll().stream()
                .filter(BatterySwapStation::getIsActive)
                .map(station -> new StationWithDistance(station, calculateDistance(userLat, userLon,
                        station.getLatitude(), station.getLongitude())))
                .sorted(Comparator.comparingDouble(StationWithDistance::distance))
                .map(StationWithDistance::station)
                .collect(Collectors.toList());
    }

    // ✅ Reserve one charged battery (if available)
    public BatterySwapStation reserveBattery(Long stationId) {
        BatterySwapStation station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));

        if (!Boolean.TRUE.equals(station.getIsActive())) {
            throw new IllegalStateException("Station is not active");
        }

        if (station.getChargedBatteries() == null || station.getChargedBatteries() <= 0) {
            throw new IllegalStateException("No charged batteries available to reserve");
        }

        // Reserve logic
        station.setChargedBatteries(station.getChargedBatteries() - 1);
        stationRepository.save(station);

        return station;
    }

    // 🔍 Haversine formula for distance in KM
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    // 🧱 Helper record
    private record StationWithDistance(BatterySwapStation station, double distance) {}
}
