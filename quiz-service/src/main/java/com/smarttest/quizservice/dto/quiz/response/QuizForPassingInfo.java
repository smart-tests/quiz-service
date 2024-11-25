package com.smarttest.quizservice.dto.quiz.response;

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
public class QuizForPassingInfo {

    private UUID id;

    private String title;

    private String theme;

    private Integer questionsNumber;

    private String groupName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
