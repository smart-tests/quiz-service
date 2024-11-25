package com.smarttest.quizservice.dto.quiz.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassingQuestionRequest {

    private UUID id;

    private String textAnswer;

    private List<UUID> answerIds;
}
