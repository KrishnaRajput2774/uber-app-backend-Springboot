package com.rk.uberApp.controllers;


import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.services.Implementations.RiderServiceImpl;
import com.rk.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/rideRequest")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto) {
        RideRequestDto ride = riderService.requestRide(rideRequestDto);
        return ResponseEntity.ok(ride);
    }


}
