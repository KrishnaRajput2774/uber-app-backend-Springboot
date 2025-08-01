package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideStartOtpDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;

import java.util.List;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String rideStartOtpDto);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

    Driver getCurrentDriver();
}
