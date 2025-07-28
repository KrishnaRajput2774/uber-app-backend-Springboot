package com.rk.uberApp.services;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.SignupDto;

public interface AuthService {

    String login( String name, String password);

    SignupDto signup(SignupDto signupDto);

    DriverDto onBoard(Long id);

}
