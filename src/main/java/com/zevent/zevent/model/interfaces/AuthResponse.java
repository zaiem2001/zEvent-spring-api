package com.zevent.zevent.model.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String token;
}
