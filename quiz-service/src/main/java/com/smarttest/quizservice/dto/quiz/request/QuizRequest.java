package com.smarttest.quizservice.dto.quiz.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class QuizRequest {

    private String id;

    private String title;

    private String theme;

    @JsonProperty(defaultValue = "true")
    private Boolean isEditable;

    private List<QuestionRequest> questions;
}
