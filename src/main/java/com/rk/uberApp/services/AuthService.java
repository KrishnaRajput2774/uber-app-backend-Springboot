package com.rk.uberApp.services;

import com.rk.uberApp.dtos.*;

public interface AuthService {

    String[] login( String name, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onBoardNewDriver(Long userId, VehicleDto vehicleDto);

    String refreshToken(String refreshToken);

}
