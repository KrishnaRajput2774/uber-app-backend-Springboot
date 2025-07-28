package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RiderDto;

import java.util.List;

public interface DriverService {

    RiderDto acceptRide(Long rideId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();
}
