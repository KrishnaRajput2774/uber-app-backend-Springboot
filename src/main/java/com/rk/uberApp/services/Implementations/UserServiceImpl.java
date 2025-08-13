package com.rk.uberApp.services.Implementations;

import com.rk.uberApp.entities.User;
import com.rk.uberApp.exceptions.ResourceNotFoundException;
import com.rk.uberApp.repositories.UserRepository;
import com.rk.uberApp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found with Username: "+username));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("User not found with id: "+userId));
    }
}
