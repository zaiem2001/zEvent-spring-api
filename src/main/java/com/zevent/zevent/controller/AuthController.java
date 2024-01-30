package com.zevent.zevent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zevent.zevent.model.interfaces.AuthLoginBody;
import com.zevent.zevent.model.interfaces.AuthReqBody;
import com.zevent.zevent.model.interfaces.AuthResponse;
import com.zevent.zevent.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthReqBody requestBody) {
        return new ResponseEntity<>(authService.register(requestBody), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginBody requestBody) {
        return new ResponseEntity<>(authService.login(requestBody), HttpStatus.OK);
    }

}
