package com.smarttest.quizservice.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralUserInfo {

    private UUID id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;
}
