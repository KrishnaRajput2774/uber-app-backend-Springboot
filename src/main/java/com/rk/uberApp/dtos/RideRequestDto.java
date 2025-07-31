package com.rk.uberApp.dtos;

import com.rk.uberApp.entities.enums.PaymentMethod;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {


    private Long id;

    private PointDto pickUpLocation;
    private PointDto dropOffLocation;

    private Double fare;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;

}
