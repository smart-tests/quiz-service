package com.smarttest.quizservice.dto.group.response;

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
public class CreateGroupResponse {

    private UUID id;

    private UUID ownerId;

    private String name;

    private String description;
}
