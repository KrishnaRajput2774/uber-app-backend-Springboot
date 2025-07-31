package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.strategies.DriverMatchStrategy;

import java.util.List;

public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return List.of();
    }
}
