package com.battery.swap.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "battery_swap_station")
public class BatterySwapStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String city;
    private String batteryType; // e.g. AC_LEVEL1, DC_FAST
    private Double batteryRate;
    private Integer totalBatteries;
    private Integer chargedBatteries;
    private Boolean isActive;
    private String connectorType; // e.g. CCS, Type2
    private Timestamp lastUpdated;
}
