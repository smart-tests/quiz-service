package com.smarttest.quizservice.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedQuizResponse {

    private String id;

    private String title;

    private String theme;

    private Boolean isEditable;

    private List<GeneratedQuizQuestionDto> questions;
}
