package com.rk.uberApp.services;

import com.rk.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);


    void update(RideRequest rideRequest);
}

