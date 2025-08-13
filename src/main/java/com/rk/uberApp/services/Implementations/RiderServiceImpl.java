package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.*;
import com.rk.uberApp.entities.*;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.entities.enums.RideStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.RideRequestRepository;
import com.rk.uberApp.repositories.RiderRepository;
import com.rk.uberApp.services.DriverService;
import com.rk.uberApp.services.RatingService;
import com.rk.uberApp.services.RideService;
import com.rk.uberApp.services.RiderService;
import com.rk.uberApp.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;


    @Transactional
    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        //dummy rider
        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        //calculating Fare
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        //saving ride request to database
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);


        //matching driver
        List<Driver> drivers = rideStrategyManager
                .driverMatchStrategy(rider.getRating()).findMatchingDriver(rideRequest);
        //TODO after getting list of driver we will need to send notification to all drivers about requested ride

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider currentRider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!ride.getRider().equals(currentRider)) {
            throw new RuntimeException("Rider does not Owns This ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Cannot Cancel Ride " + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);

        Driver driver = ride.getDriver();
        Driver savedDriver = driverService.updateDriverAvailability(driver, true);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Double rating) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Cant Rate Driver, Because Rider does not owns this Ride");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride is NOT ENDED hence cannot Rate Driver, STATUS: " + ride.getRideStatus());
        }

        return ratingService.rateDriver(ride,rating);

    }

    @Override
    public RiderDto getMyProfile() {

        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);

    }


    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Rider createRider(User mappedUser) {

        Rider rider = Rider
                .builder()
                .user(mappedUser)
                .rating(0.0)
                .build();

        log.info("Rider-------> {}",rider.toString());

        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        //TODO currently sending dummy rider
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Rider Not Associated With User id: " + user.getId()));
    }
}
