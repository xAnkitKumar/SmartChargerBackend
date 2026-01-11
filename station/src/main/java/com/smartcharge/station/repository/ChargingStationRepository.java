package com.smartcharge.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcharge.station.model.ChargingStation;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, Integer> {

}