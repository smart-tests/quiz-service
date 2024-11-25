package com.smarttest.quizservice.dto.group.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddGroupMemberRequest {

    private UUID groupId;

    private String memberLogin;
}
