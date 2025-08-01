package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.entities.RideRequest;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.RideRequestRepository;
import com.rk.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(()->new ResourceNotFoundException("RideRequest not found with id"+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {

        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("RideRequest Not Found with id: "+rideRequest.getId()));

        rideRequestRepository.save(rideRequest);

    }
}
