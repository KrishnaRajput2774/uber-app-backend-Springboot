package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.RideRequestRepository;
import com.rk.uberApp.repositories.RiderRepository;
import com.rk.uberApp.services.RiderService;
import com.rk.uberApp.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Transactional
    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        //dummy rider
        Rider rider = currentRider();

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

        return modelMapper.map(savedRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public Rider createRider(User mappedUser) {

        Rider rider = Rider
                .builder()
                .user(mappedUser)
                .rating(0.0)
                .build();

        return riderRepository.save(rider);
    }

    @Override
    public Rider currentRider() {
        //TODO currently sending dummy rider
        return riderRepository.findById(1L).orElseThrow(()->new ResourceNotFoundException("Rider Not Found With id: "+1));
    }
}
