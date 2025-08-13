package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Rating;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.exceptions.RuntimeConflictException;
import com.rk.uberApp.repositories.DriverRepository;
import com.rk.uberApp.repositories.RatingRepository;
import com.rk.uberApp.repositories.RiderRepository;
import com.rk.uberApp.services.DriverService;
import com.rk.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;


    @Override
    public DriverDto rateDriver(Ride ride, Double rating) {

        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating Not Found With Ride Id " + ride.getId()));

        if(ratingObj.getDriverRating() != null ) {
            throw new RuntimeConflictException("Driver has already been Rated");
        }
        ratingObj.setDriverRating(rating);

        Double newRatingAvg = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRatingAvg);
        Driver savedDriver = driverRepository.save(driver);

        return modelMapper.map(driver,DriverDto.class);

    }

    @Override
    public RiderDto rateRider(Ride ride, Double rating) {

        Rider rider = ride.getRider();

        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating Not Found With Ride Id " + ride.getId()));

        if(ratingObj.getRiderRating() != null ) {
            throw new RuntimeConflictException("Driver has already been Rated");
        }

        ratingObj.setRiderRating(rating);

        Double newRatingAvg = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRatingAvg);
        Rider savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider,RiderDto.class);
    }

    @Override
    public void createRating(Ride ride) {

        Rating rating = Rating.builder()
                .driver(ride.getDriver())
                .rider(ride.getRider())
                .ride(ride)
                .build();

        ratingRepository.save(rating);

    }
}
