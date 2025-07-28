package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.SignupDto;
import com.rk.uberApp.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String login(String name, String password) {
        return "";
    }

    @Override
    public SignupDto signup(SignupDto signupDto) {
        return null;
    }

    @Override
    public DriverDto onBoard(Long id) {
        return null;
    }
}
