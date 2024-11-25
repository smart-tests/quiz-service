package com.smarttest.quizservice.dto.quiz.request;

import com.smarttest.quizservice.dao.entities.QuestionType;
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
public class QuestionRequest {

    private String id;

    private QuestionType type;

    private String text;

    private List<AnswerRequest> answers;
}
