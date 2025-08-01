package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideStartOtpDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.entities.enums.RideStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.DriverRepository;
import com.rk.uberApp.services.DriverService;
import com.rk.uberApp.services.RideRequestService;
import com.rk.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final RideService rideService;
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Override
    public RideDto acceptRide(Long rideId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideId);
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("Ride Has been Accepted by Another Driver: Status " + rideRequest.getRideRequestStatus());
        }

        Driver driver = getCurrentDriver();
        if (!driver.getAvailable()) {
            throw new RuntimeException("Driver cannot accept Ride because of availability");
        }

        Ride ride = rideService.createNewRide(rideRequest, driver);
        driver.setAvailable(false);
        driverRepository.save(driver);

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId, String rideStarOtp) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();         // this is dummy driver

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start the ride because he has not accepted the RideRequest");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride is NOT CONFIRMED hence cannot be started, STATUS: " + ride.getRideStatus());
        }

        if (!ride.getOtp().equals(rideStarOtp)) {
            throw new RuntimeException("INVALID OTP");
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);


        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public Driver getCurrentDriver() {
        //Returning dummy driver
        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not Found with Id: " + 2));
    }
}
