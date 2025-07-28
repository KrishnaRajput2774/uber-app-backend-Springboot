package com.rk.uberApp.dtos;

import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.enums.PaymentMethod;
import com.rk.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDto {

    private Long id;

    private Point pickUpLocation;

    private Point dropOffLocation;

    private LocalDateTime createdTime;    //Driver Accepts the ride

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;
    private LocalDateTime startedAt; //rides starts
    private LocalDateTime endedAt;

}
