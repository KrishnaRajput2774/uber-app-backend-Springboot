package com.rk.uberApp.controllers;


import com.rk.uberApp.dtos.SignupDto;
import com.rk.uberApp.dtos.UserDto;
import com.rk.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public UserDto signup(@RequestBody SignupDto signupDto) {

        return authService.signup(signupDto);
    }



}
