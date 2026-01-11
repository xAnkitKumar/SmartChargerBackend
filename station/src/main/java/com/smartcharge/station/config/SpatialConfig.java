package com.smartcharge.station.config;


import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpatialConfig {
    
    @Bean
    public GeometryFactory geometryFactory() {
        // SRID 4326 is for WGS84 coordinate system (standard for GPS)
        return new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    }
}