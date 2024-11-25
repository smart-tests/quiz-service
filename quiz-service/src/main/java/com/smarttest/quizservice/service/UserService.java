package com.smarttest.quizservice.service;

import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dao.repository.UserRepository;
import com.smarttest.quizservice.dto.user.SecUser;
import com.smarttest.quizservice.dto.user.request.CreateUserRequest;
import com.smarttest.quizservice.dto.user.response.CreateUserResponse;
import com.smarttest.quizservice.dto.user.response.GeneralUserInfo;
import com.smarttest.quizservice.mapper.UserMapper;
import com.smarttest.quizservice.util.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public CreateUserResponse createUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toUserEntity(request, encodedPassword, "USER");
        User savedUser = userRepository.save(user);

        return userMapper.toCreateUserResponse(savedUser);
    }

    public GeneralUserInfo getCurrentUserGeneralInfo() {
        SecUser secUser = getCurrentSecUser();
        User currentUser = userRepository.findById(secUser.getId())
                .orElseThrow(() -> new ServiceException(
                        404,
                        "User not found: userId=" + secUser.getId()
                ));

        return userMapper.toGeneralUserInfo(currentUser);
    }

    public SecUser getCurrentSecUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof SecUser) {
            return (SecUser) principal;
        }

        throw new ServiceException(401, "Unauthorized");
    }
}
