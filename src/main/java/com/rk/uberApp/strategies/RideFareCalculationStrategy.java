package com.rk.uberApp.strategies;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    Double RIDE_FARE_MULTIPLIYER = 10.00;

    Double calculateFare(RideRequest rideRequest);
}
