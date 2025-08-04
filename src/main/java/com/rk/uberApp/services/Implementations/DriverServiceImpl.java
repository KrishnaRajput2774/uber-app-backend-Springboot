package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.entities.enums.RideStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.DriverRepository;
import com.rk.uberApp.repositories.RideRequestRepository;
import com.rk.uberApp.services.DriverService;
import com.rk.uberApp.services.PaymentService;
import com.rk.uberApp.services.RideRequestService;
import com.rk.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final RideService rideService;
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RideRequestRepository rideRequestRepository;

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

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        rideRequestRepository.save(rideRequest);

        Driver savedDriver = updateDriverAvailability(driver, false);  //driver accepted ride
        Ride ride = rideService.createNewRide(rideRequest, savedDriver);


        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!ride.getDriver().equals(driver)) {   // checking if the driver owns this ride or not which he is trying to cancel ride
            throw new RuntimeException(driver.getUser().getName() + " Does not belongs to This Ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {   // checking if ride status is not CONFIRMED then he cannot cancel the ride
            throw new RuntimeException("Cannot Cancel the Ride, Invalid Status " + ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDto.class);

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

        //When ride is CONFIRMED and Started then Add Payment
        paymentService.createNewPayment(savedRide);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start the ride because he has not accepted the RideRequest");
        }

        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride is NOT ONGOING   hence cannot be ENDED, STATUS: " + ride.getRideStatus());
        }

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideId);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        rideRequestRepository.save(rideRequest);



        rideService.updateRideStatus(ride,RideStatus.ENDED);

        ride.setEndedAt(LocalDateTime.now());
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);
        

        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {

        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Driver driver = getCurrentDriver();

        return rideService.getAllRidesOfDriver(driver, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );

    }

    @Override
    public Driver getCurrentDriver() {
        //Returning dummy driver
        return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not Found with Id: " + 2));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }
}