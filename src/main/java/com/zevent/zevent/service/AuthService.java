package com.zevent.zevent.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zevent.zevent.config.JwtService;
import com.zevent.zevent.exceptions.CustomException;
import com.zevent.zevent.model.User;
import com.zevent.zevent.model.enums.RoleEnum;
import com.zevent.zevent.model.interfaces.AuthLoginBody;
import com.zevent.zevent.model.interfaces.AuthReqBody;
import com.zevent.zevent.model.interfaces.AuthResponse;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(AuthReqBody requestBody) {
        Optional<User> dbUser = userService.findByEmail(requestBody.getEmail());
        if (dbUser.isPresent()) {
            throw new CustomException("User already exists!");
        }

        User user = User.builder()
                .username(requestBody.getUsername())
                .email(requestBody.getEmail())
                .password(passwordEncoder.encode(requestBody.getPassword()))
                .phoneNumber(requestBody.getPhoneNumber())
                .role(assignRole(requestBody.getUsername()))
                .build();

        var savedUser = userService.createUser(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(savedUser.get_id().toHexString(), savedUser.getUsername(), savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                jwtToken);

    }

    public AuthResponse login(AuthLoginBody userDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword()));

        User user = userService.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(user.get_id().toHexString(), user.getUsername(), user.getEmail(), user.getPhoneNumber(),
                jwtToken);

    }

    private RoleEnum assignRole(String username) {
        return username.toLowerCase().contains("zaiem") ? RoleEnum.ADMIN : RoleEnum.USER;
    }

}
