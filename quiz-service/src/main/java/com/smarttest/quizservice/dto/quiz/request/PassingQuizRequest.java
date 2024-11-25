package com.smarttest.quizservice.dto.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassingQuizRequest {

    private List<PassingQuestionRequest> questions;
}
