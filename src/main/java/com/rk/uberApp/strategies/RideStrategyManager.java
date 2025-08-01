package com.rk.uberApp.strategies;


import com.rk.uberApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.rk.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.rk.uberApp.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.rk.uberApp.strategies.impl.RideFareSurgeCalculationFareStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;

    private final RideFareSurgeCalculationFareStrategy rideFareSurgeCalculationFareStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;



    public DriverMatchStrategy driverMatchStrategy(double riserRating) {

        if( riserRating > 4.2) {
            return driverMatchingHighestRatedDriverStrategy;
        }
        else {
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeENdTime = LocalTime.of(21,0);

        LocalTime currentTime = LocalTime.now();

        if( currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeENdTime) ) {
            return rideFareSurgeCalculationFareStrategy;
        }
        else {
            return rideFareDefaultFareCalculationStrategy;
        }
    }




}
