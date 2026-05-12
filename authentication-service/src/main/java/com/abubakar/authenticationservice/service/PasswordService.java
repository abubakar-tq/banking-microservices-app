package com.abubakar.authenticationservice.service;

import com.abubakar.authenticationservice.dto.ChangePasswordRequestDTO;

public interface PasswordService {

    void requestCodeToResetPassword(String email);

    void resetPassword(ChangePasswordRequestDTO dto);

}
