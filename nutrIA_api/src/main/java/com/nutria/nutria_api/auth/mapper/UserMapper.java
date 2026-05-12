package com.nutria.nutria_api.auth.mapper;

import com.nutria.nutria_api.auth.dto.RegisterUserResponse;
import com.nutria.nutria_api.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastNames", source = "last_names")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "birthDate", source = "birth_date")
    RegisterUserResponse toRegisterUserResponse(User user);
}
