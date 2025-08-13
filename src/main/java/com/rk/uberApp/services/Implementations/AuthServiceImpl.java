package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.dtos.*;
import com.rk.uberApp.entities.Driver;
import com.rk.uberApp.entities.Rider;
import com.rk.uberApp.entities.User;
import com.rk.uberApp.entities.enums.Role;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.exceptions.RuntimeConflictException;
import com.rk.uberApp.repositories.UserRepository;
import com.rk.uberApp.security.JwtService;
import com.rk.uberApp.services.AuthService;
import com.rk.uberApp.services.DriverService;
import com.rk.uberApp.services.RiderService;
import com.rk.uberApp.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String[] login(String name, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name,password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateJwtAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};

    }

    @Transactional
    @Override
    public UserDto signup(SignupDto signupDto) {

        // check if user exists already
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if( user != null) {
           throw new RuntimeConflictException("User Already exists "+signupDto.getEmail());
        }

        User mappedUser = modelMapper.map(signupDto, User.class);

        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        //Creating Rider
        Rider savedRider = riderService.createRider(savedUser);

        //TODO Add Wallet related Service here
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId, VehicleDto vehicleDto) {

        User user = userRepository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User not Found with Id (Driver needs to be user first)"+userId));

        if(user.getRoles().contains(Role.DRIVER))
            throw new RuntimeException(user.getName()+" with id: +"+user.getId()+" is already DRIVER");

        Driver createDriver = Driver.builder()
                .available(true)
                .rating(0.0)
                .vehicleId(vehicleDto.getVehicleId())      //TODO can make vehicle entity separately (vehicleId, VehicleType)
                .user(user)                                //TODO add it in Driver entity
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        return modelMapper.map(driverService.createNewDriver(createDriver),DriverDto.class);

    }

    @Override
    public String refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not Found With This Refresh Token: "+refreshToken));

        return jwtService.generateJwtAccessToken(user);
    }

}
