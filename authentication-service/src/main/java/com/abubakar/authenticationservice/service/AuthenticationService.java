package com.abubakar.authenticationservice.service;

import com.abubakar.authenticationservice.dto.LoginRequestDTO;
import com.abubakar.authenticationservice.dto.LoginResponseDTO;

public interface AuthenticationService {

    LoginResponseDTO authenticate(LoginRequestDTO dto);
}
