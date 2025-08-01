package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.repositories.DriverRepository;
import com.rk.uberApp.strategies.DriverMatchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchStrategy {


    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTopTenHighRatedMatchingDrivers(rideRequest.getPickUpLocation());
    }
}