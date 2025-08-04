package com.rk.uberApp.services;

import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    RiderDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createRider(User mappedUser);

    Rider getCurrentRider();
}
