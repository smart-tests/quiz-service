package com.smarttest.quizservice.service.checking;

import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.QuestionType;
import com.smarttest.quizservice.dto.quiz.request.PassingQuestionRequest;

public interface AnswerCheckStrategy {

    boolean isCorrect(PassingQuestionRequest selected, Question correct);

    QuestionType getType();
}
