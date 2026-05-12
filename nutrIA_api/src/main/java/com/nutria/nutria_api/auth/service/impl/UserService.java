package com.nutria.nutria_api.auth.service.impl;

import com.nutria.nutria_api.auth.dto.RegisterUserRequest;
import com.nutria.nutria_api.auth.dto.RegisterUserResponse;
import com.nutria.nutria_api.auth.exception.EmailAlreadyExistsException;
import com.nutria.nutria_api.auth.mapper.UserMapper;
import com.nutria.nutria_api.auth.model.Gender;
import com.nutria.nutria_api.auth.model.Role;
import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.auth.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public RegisterUserResponse register(RegisterUserRequest userRequest) {
        // RN-01: Unicidad de cuenta por correo
        System.out.println("DTO RECIBIDO: " + userRequest); // <-- Agrega esto
        if(userRepository.existsByEmail(userRequest.email())) {
            throw new EmailAlreadyExistsException(userRequest.email());
        }

        User user = User.builder()
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .name(userRequest.name())
                .last_names(userRequest.lastNames())
                .birth_date(LocalDate.parse((userRequest.birthDate()), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .gender(Gender.valueOf(userRequest.gender()))
                .role(Role.valueOf(userRequest.userType()))
                .build();

        userRepository.save(user);

        return userMapper.toRegisterUserResponse(user);
    }
}
