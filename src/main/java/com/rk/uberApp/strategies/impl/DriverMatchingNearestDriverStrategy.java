package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.strategies.DriverMatchStrategy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchStrategy {

    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
