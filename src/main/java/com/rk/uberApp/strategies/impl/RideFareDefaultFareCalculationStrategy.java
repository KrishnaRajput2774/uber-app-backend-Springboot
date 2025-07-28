package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.strategies.RideFareCalculationStrategy;

public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {


    @Override
    public Double calculateFare(RideRequestDto rideRequestDto) {
        return 0.0;
    }
}
