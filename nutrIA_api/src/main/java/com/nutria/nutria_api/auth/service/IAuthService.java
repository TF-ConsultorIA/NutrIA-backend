package com.nutria.nutria_api.auth.service;

import com.nutria.nutria_api.auth.dto.AuthResponse;
import com.nutria.nutria_api.auth.dto.LoginRequest;

public interface IAuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refresh(String refreshToken);
    void logout(String refreshToken);
    void logoutAll(String email);
}
