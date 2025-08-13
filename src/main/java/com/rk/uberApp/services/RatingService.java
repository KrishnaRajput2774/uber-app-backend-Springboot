package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.Rider;

public interface RatingService {



    DriverDto rateDriver(Ride ride, Double rating);
    RiderDto rateRider(Ride ride, Double rating);


    void createRating(Ride ride);
}
