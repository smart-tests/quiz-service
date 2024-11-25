package com.smarttest.quizservice.service.checking;

import com.smarttest.quizservice.dao.entities.Answer;
import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.QuestionType;
import com.smarttest.quizservice.dto.quiz.request.PassingQuestionRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MultiAnswerChecker implements AnswerCheckStrategy {
    @Override
    public boolean isCorrect(PassingQuestionRequest selected, Question correct) {
        List<UUID> correctAnswerIds = correct.getAnswers().stream()
                .filter(Answer::getIsRight)
                .map(Answer::getId)
                .toList();

        return CollectionUtils.isEqualCollection(correctAnswerIds, selected.getAnswerIds());
    }

    @Override
    public QuestionType getType() {
        return QuestionType.MULTI_ANSWER;
    }
}
