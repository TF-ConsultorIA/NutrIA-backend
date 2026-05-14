package com.nutria.nutria_api.auth.service;

import com.nutria.nutria_api.auth.dto.RegisterUserRequest;
import com.nutria.nutria_api.auth.dto.UpdateUserRequest;
import com.nutria.nutria_api.auth.dto.UserResponse;

public interface IUserService {
    UserResponse register(RegisterUserRequest userRequest);
    UserResponse findByEmail(String email);
    UserResponse updateByEmail(String email, UpdateUserRequest userRequest);
    void delete(Long id);
}
