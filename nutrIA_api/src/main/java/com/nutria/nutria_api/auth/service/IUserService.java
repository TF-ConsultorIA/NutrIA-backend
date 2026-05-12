package com.nutria.nutria_api.auth.service;

import com.nutria.nutria_api.auth.dto.RegisterUserRequest;
import com.nutria.nutria_api.auth.dto.RegisterUserResponse;
import com.nutria.nutria_api.auth.repository.UserRepository;

public interface IUserService {
    RegisterUserResponse register(RegisterUserRequest userRequest);
}
