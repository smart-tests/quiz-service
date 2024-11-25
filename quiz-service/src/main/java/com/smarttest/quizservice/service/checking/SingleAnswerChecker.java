package com.smarttest.quizservice.service.checking;

import com.smarttest.quizservice.dao.entities.Answer;
import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.QuestionType;
import com.smarttest.quizservice.dto.quiz.request.PassingQuestionRequest;
import com.smarttest.quizservice.util.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class SingleAnswerChecker implements AnswerCheckStrategy{
    @Override
    public boolean isCorrect(PassingQuestionRequest selected, Question correct) {
        if (selected.getAnswerIds().size() != 1) {
            throw new ServiceException(400, "Single answer question contains multiple answers: questionId=" + selected.getId());
        }
        Answer correctAnswer = correct.getAnswers()
                .stream()
                .filter(answer -> answer.getIsRight())
                .findFirst().orElseThrow(() -> new ServiceException(
                        500,
                        "Single answer question not contains correct answer: questioId=" + correct.getId()));

        return selected.getAnswerIds().get(0).equals(correctAnswer.getId());
    }

    @Override
    public QuestionType getType() {
        return QuestionType.SINGLE_ANSWER;
    }
}
