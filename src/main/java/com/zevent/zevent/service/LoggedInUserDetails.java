package com.zevent.zevent.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zevent.zevent.model.User;

@Service
public class LoggedInUserDetails {

    public User getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        return userDetails;
    }
}
