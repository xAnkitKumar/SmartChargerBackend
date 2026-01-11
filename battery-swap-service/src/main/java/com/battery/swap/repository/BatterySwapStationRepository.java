package com.battery.swap.repository;

import com.battery.swap.entity.BatterySwapStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatterySwapStationRepository extends JpaRepository<BatterySwapStation, Long> {

}
