package com.rk.uberApp.strategies;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);

}
