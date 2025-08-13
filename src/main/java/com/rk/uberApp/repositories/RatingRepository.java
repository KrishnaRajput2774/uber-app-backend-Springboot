package com.rk.uberApp.repositories;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Rating;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);


    Optional<Rating> findByRide(Ride ride);
}
