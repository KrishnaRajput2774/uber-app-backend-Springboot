package com.rk.uberApp.strategies.impl;

import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.services.DistanceService;
import com.rk.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RideFareSurgeCalculationFareStrategy implements RideFareCalculationStrategy {


    private static final Double SURGE_FACTOR = 2.0;
    private final DistanceService distanceService;


    @Override
    public Double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIYER * SURGE_FACTOR;
        //TODO make a surge factor based on wheaten or traffic jam roads condition or NY EVENT USING THIRD PARTY API
    }
}
