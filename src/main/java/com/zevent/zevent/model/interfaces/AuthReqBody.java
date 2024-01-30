package com.zevent.zevent.model.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqBody {
    private String email;
    private String password;
    private String username;
    private String phoneNumber;

}
