package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.services.DistanceService;
import com.rk.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());
        return distance  * RIDE_FARE_MULTIPLIYER;
    }
}
