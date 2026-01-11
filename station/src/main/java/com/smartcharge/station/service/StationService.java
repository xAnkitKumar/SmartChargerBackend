package com.smartcharge.station.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.smartcharge.station.model.ChargingStation;
import com.smartcharge.station.repository.ChargingStationRepository;

@Service
public class StationService {

    private final ChargingStationRepository repository;

    public StationService(ChargingStationRepository repository) {
        this.repository = repository;
    }

    public List<ChargingStationWithDistance> findStationsWithDistance(double userLat, double userLon) {
        String detectedCity = getCityFromCoordinates(userLat, userLon);
        if (detectedCity == null) {
            throw new RuntimeException("City not found!");
        }

        String userCity = normalizeCityName(detectedCity);

        if (userCity.equals("Bangalore") || userCity.equals("Gurugram") || userCity.equals("Mumbai") || userCity.equals("Hyderabad")) {
            return repository.findAll().stream()
                .filter(s -> s.getCity() != null && s.getCity().equalsIgnoreCase(userCity))
                .map(station -> {
                    double distance = calculateDistance(userLat, userLon, station.getLatitude(), station.getLongitude());
                    return new ChargingStationWithDistance(station, distance);
                })
                .sorted(Comparator.comparingDouble(ChargingStationWithDistance::distance))
                .toList();
        } else {
            System.out.println("City not supported: " + userCity);
            throw new RuntimeException("City not supported: " + userCity);
        }
    }

    private String normalizeCityName(String city) {
        city = city.trim();
        if (city.equalsIgnoreCase("Bengaluru")) return "Bangalore";
        if (city.equalsIgnoreCase("Gurgaon")) return "Gurugram";
        if (city.toLowerCase().contains("mumbai")) return "Mumbai";
        return city;
    }
    
    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public record ChargingStationWithDistance(ChargingStation station, double distance) {
        
    }

    public String getCityFromCoordinates(double lat, double lon) {
    try {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "ev-charging-app")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject obj = new JSONObject(response.body());
        JSONObject address = obj.getJSONObject("address");

        if (address.has("city"))
            return address.getString("city");
        else
            return null;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}