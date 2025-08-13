package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RatingDto;
import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String rideStartOtpDto);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Double rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, Boolean available);

    Driver createNewDriver(Driver driver);

}
