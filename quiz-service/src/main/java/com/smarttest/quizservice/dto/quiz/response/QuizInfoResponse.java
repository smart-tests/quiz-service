package com.smarttest.quizservice.dto.quiz.response;

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
public class QuizInfoResponse {

    private UUID id;

    private String title;

    private String theme;

    private Boolean isEditable;

    private Integer questionsNumber;
}
