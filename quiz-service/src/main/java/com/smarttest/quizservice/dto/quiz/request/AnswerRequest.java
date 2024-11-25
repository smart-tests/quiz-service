package com.smarttest.quizservice.dto.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRequest {

    private String id;

    private String text;

    private Boolean isRight;
}
