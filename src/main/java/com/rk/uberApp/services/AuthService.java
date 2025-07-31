package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.SignupDto;
import com.rk.uberApp.dtos.UserDto;

public interface AuthService {

    String login( String name, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onBoard(Long id);

}
