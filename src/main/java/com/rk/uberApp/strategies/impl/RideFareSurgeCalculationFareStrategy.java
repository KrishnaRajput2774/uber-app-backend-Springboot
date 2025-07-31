package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.strategies.RideFareCalculationStrategy;

public class RideFareSurgeCalculationFareStrategy implements RideFareCalculationStrategy {

    @Override
    public Double calculateFare(RideRequest rideRequestDto) {
        return 0.0;
    }
}
