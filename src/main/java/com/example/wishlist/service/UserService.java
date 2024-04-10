package com.example.wishlist.service;

import com.example.wishlist.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDetailsManager userDetailsManager;

    public UserService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public void registerUser(UserDto user) {
        UserDetails userToRegister = User.builder()
                .username(user.username())
                .password(user.password())
                .roles("USER").build();
        userDetailsManager.createUser(userToRegister);
    }

}
