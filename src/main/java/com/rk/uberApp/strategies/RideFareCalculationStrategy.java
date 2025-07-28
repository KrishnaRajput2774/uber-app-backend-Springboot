package com.rk.uberApp.strategies;

import com.rk.uberApp.dtos.RideRequestDto;

public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequestDto rideRequestDto);
}
