package com.rk.uberApp.repositories;

import com.rk.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {


    //ST_Distance(point1, point2)
    //ST_DWithin(point1, point2, 10000)

    @Query(value = "select d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM drivers d " +
            "WHERE d.available = true AND " +
            "ST_DWithin(d.current_location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<Driver> findTopTenNearestMatchingDrivers(Point pickUpLocation);
}
