package com.rk.uberApp.services;

import com.rk.uberApp.dtos.*;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Double rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createRider(User mappedUser);

    Rider getCurrentRider();
}
