package com.smarttest.quizservice.dto.quiz;

import com.smarttest.quizservice.dao.entities.QuestionType;
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
public class GeneratedQuizQuestionDto {

    private String id;

    private String text;

    private QuestionType type;

    private List<GeneratedQuizAnswerDto> answers;
}
