package com.smarttest.quizservice.service.checking;

import com.smarttest.quizservice.dao.entities.Answer;
import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.QuestionType;
import com.smarttest.quizservice.dto.quiz.request.PassingQuestionRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InputAnswerChecker implements AnswerCheckStrategy {
    @Override
    public boolean isCorrect(PassingQuestionRequest selected, Question correct) {
        if (selected.getTextAnswer() == null || selected.getTextAnswer().isBlank()) {
            return false;
        }

        List<String> correctAnswers = correct.getAnswers().stream()
                .map(Answer::getText)
                .toList();

        return correctAnswers.contains(selected.getTextAnswer());
    }

    @Override
    public QuestionType getType() {
        return QuestionType.INPUT_ANSWER;
    }
}
