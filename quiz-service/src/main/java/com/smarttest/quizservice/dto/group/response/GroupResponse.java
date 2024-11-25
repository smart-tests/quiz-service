package com.smarttest.quizservice.dto.group.response;

import com.smarttest.quizservice.dto.user.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    private UUID id;

    private UUID ownerId;

    private String name;

    private String description;

    private List<GroupMember> members;

    private Integer quizzesCount;
}
