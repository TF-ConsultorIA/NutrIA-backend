package com.nutria.nutria_api.auth.service.impl;

import com.nutria.nutria_api.auth.dto.RegisterUserRequest;
import com.nutria.nutria_api.auth.dto.UpdateUserRequest;
import com.nutria.nutria_api.auth.dto.UserResponse;
import com.nutria.nutria_api.auth.exception.EmailAlreadyExistsException;
import com.nutria.nutria_api.auth.exception.NotStudentEmailException;
import com.nutria.nutria_api.auth.exception.UsernameNotFoundException;
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
    // US-01 : Registro de usuario
    public UserResponse register(RegisterUserRequest userRequest) {
        // RN-01 : Unicidad de cuenta por correo
        if(userRepository.existsByEmail(userRequest.email())) {
            throw new EmailAlreadyExistsException(userRequest.email());
        }

        // RN-26 : Registro de correo estudiantil
        if (Role.valueOf(userRequest.userType()) == Role.YOUNGER &&
                !userRequest.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.edu(\\.[a-zA-Z]{2})?$")) {
            throw new NotStudentEmailException();
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

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );
        return userMapper.toRegisterUserResponse(user);
    }

    @Override
    @Transactional
    // US-03 : Actualización de información personal
    public UserResponse updateByEmail(String email, UpdateUserRequest userRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );

        user.setName(userRequest.name());
        user.setLast_names(userRequest.lastNames());
        user.setBirth_date(LocalDate.parse(userRequest.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        user.setGender(Gender.valueOf(userRequest.gender()));

        return userMapper.toRegisterUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
