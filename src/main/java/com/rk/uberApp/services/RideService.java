package com.rk.uberApp.services;

import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    void matchWithDriver(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequestDto rideRequestDto, Driver driver);

    Ride updateRideStatus(Long rideId, RideStatus rideStatus);

    Page<Ride> getAllRidesOdRider(Long riderId, PageRequest pageRequest);

    Page<Ride> getAllRidesOdDriver(Long driverId, PageRequest pageRequest);

}
