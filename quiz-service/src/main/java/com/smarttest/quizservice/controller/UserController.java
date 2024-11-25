package com.smarttest.quizservice.controller;

import com.smarttest.quizservice.dto.user.request.CreateUserRequest;
import com.smarttest.quizservice.dto.user.response.CreateUserResponse;
import com.smarttest.quizservice.dto.user.response.GeneralUserInfo;
import com.smarttest.quizservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/login")
    public void login(){}

    @PostMapping("/logout")
    public void logout(){}

    @GetMapping("/info")
    public ResponseEntity<GeneralUserInfo> getCurUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUserGeneralInfo());
    }
}
