package com.nutria.nutria_api.auth.service;

import com.nutria.nutria_api.auth.dto.*;

public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refresh(String refreshToken);
    void logout(String refreshToken);
    void logoutAll(String email);
    ChangeCredentialsResponse changeEmail(String email, ChangeEmailRequest request);
    ChangeCredentialsResponse changePassword(String email, ChangePasswordRequest request);
}
