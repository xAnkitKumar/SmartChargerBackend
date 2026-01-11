package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import entity.ChargingStations;
import entity.ChargingStations.ChargerType;
import entity.ChargingStations.City;

@Service
public class EVRoutePlanner {

    public ChargingStations startRouting(Double startLat, Double startLon, Double endLat, Double endLon, Integer range) {
        // Implement the routing logic here
        List<ChargingStations> stations = new ArrayList<>();
        //make a call to the charging station API to get the list of stations
          ChargingStations station1 = new ChargingStations();
        station1.setName("EV Power Hub - Koramangala");
        station1.setAddress("No. 45, 80 Feet Rd, Koramangala");
        station1.setLocation(new double[]{12.9352, 77.6245}); // [lat, lon]
        station1.setChargerType(ChargerType.AC_LEVEL_2);
        station1.setTotalPorts((short)4);
        station1.setAvailablePorts((short)2);
        station1.setChargingRateKw(new BigDecimal("22.00"));
        station1.setSupportedPlugs("[\"Type 2\", \"CCS\"]");
        station1.setIsActive(true);
        station1.setCity(City.Bangalore);

        // Add more dummy stations as needed
        ChargingStations station2 = new ChargingStations();
        station2.setName("EV Fast Charge - Indiranagar");
        station2.setAddress("12th Main Rd, Indiranagar");
        station2.setLocation(new double[]{12.9716, 77.6412});
        station2.setChargerType(ChargerType.DC_FAST);
        station2.setTotalPorts((short)6);
        station2.setAvailablePorts((short)3);
        station2.setChargingRateKw(new BigDecimal("50.00"));
        station2.setSupportedPlugs("[\"CCS\", \"CHAdeMO\"]");
        station2.setIsActive(true);
        station2.setCity(City.Bangalore);

        stations.add(station1);
        stations.add(station2);

         ChargingStations nearestStation = null;
        
        double minDistance = Double.MAX_VALUE;
        //calculating the nearest charging stations based on the start and end coordinates and range from startLat, startLon to endLat, endLon
        for (ChargingStations station : stations) {
            double[] loc = (double[]) station.getLocation();
            double distance = haversine(startLat, startLon, loc[0], loc[1]);
            if (distance < minDistance) {
                minDistance = distance;
                nearestStation = station;
            }
        }

        if (nearestStation != null) {
            double[] loc = (double[]) nearestStation.getLocation();
            System.out.println("Nearest station: " + nearestStation.getName() + " at " + loc[0] + ", " + loc[1]);
        }
        return nearestStation;
    }

    // Haversine formula to calculate distance between two lat/lon points in km
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}