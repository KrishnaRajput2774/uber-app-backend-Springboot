package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.DriverDto;
import com.rk.uberApp.dtos.SignupDto;
import com.rk.uberApp.dtos.UserDto;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.enums.Role;
import com.rk.uberApp.exceptions.RuntimeConflictException;
import com.rk.uberApp.repositories.UserRepository;
import com.rk.uberApp.services.AuthService;
import com.rk.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;


    @Override
    public String login(String name, String password) {
        return "";
    }

    @Override
    public UserDto signup(SignupDto signupDto) {

        // check if user exists already
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if( user != null) {
           throw new RuntimeConflictException("User Already exists "+signupDto.getEmail());
        }

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        //Creating Rider
        Rider savedRider = riderService.createRider(savedUser);

        //TODO Add Wallet related Service here


        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onBoard(Long id) {
        return null;
    }
}
