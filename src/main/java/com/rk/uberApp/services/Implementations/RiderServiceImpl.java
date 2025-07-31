package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.RideDto;
import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.dtos.RiderDto;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.repositories.RideRepository;
import com.rk.uberApp.repositories.RideRequestRepository;
import com.rk.uberApp.repositories.RiderRepository;
import com.rk.uberApp.services.RiderService;
import com.rk.uberApp.strategies.DriverMatchStrategy;
import com.rk.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchStrategy driverMatchStrategy;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        //calculating Fare
        Double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);

        //saving ride request to database
        rideRequestRepository.save(rideRequest);

        //matching driver
        driverMatchStrategy.findMatchingDriver(rideRequest);

        return modelMapper.map(rideRequestDto,RideRequestDto.class);
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
}
