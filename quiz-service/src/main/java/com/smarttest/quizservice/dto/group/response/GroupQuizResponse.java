package com.smarttest.quizservice.dto.group.response;

import com.smarttest.quizservice.dto.quiz.response.QuizInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuizResponse {

    private UUID id;

    private UUID groupId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private QuizInfoResponse quiz;
}
