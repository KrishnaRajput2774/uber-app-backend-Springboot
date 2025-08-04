package com.rk.uberApp.controllers;


import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/riders")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/rideRequest")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto) {
        RideRequestDto rideRequestDto1  = riderService.requestRide(rideRequestDto);
        return ResponseEntity.ok(rideRequestDto1);
    }


}
