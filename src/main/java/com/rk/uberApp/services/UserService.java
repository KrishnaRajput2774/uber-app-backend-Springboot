package com.rk.uberApp.services;

import com.rk.uberApp.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findUserById(Long id);

}
