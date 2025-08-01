package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.RideRequestDto;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Ride;
import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.entities.enums.RideRequestStatus;
import com.rk.uberApp.entities.enums.RideStatus;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.RideRepository;
import com.rk.uberApp.services.RideRequestService;
import com.rk.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;
    private final RideRequestService rideRequestService;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("Ride not Found with Id: "+rideId));
    }

    @Override
    public void matchWithDriver(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateOtp());
        ride.setId(null);

        rideRequestService.update( rideRequest);

        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }



    @Override
    public Page<Ride> getAllRidesOdRider(Long riderId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOdDriver(Long driverId, PageRequest pageRequest) {
        return null;
    }

    public String generateOtp() {
        Random random = new Random();
        int num = random.nextInt(10000); // will generate 0 - 9999
        return String.format("%04d",num);      // will convert to 4 gigits 1 -> 0001

    }
}
