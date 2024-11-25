package com.smarttest.quizservice.mapper;

import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.dto.user.request.CreateUserRequest;
import com.smarttest.quizservice.dto.user.response.CreateUserResponse;
import com.smarttest.quizservice.dto.user.response.GeneralUserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "login", source = "request.login")
    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "role", source = "role")
    User toUserEntity(CreateUserRequest request, String encodedPassword, String role);

    CreateUserResponse toCreateUserResponse(User entity);

    @Mapping(target = "username", source = "login")
    SecUser toSecUser(User entity);

    GeneralUserInfo toGeneralUserInfo(User entity);
}
