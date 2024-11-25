package com.smarttest.quizservice.mapper;

import com.smarttest.quizservice.dao.entities.Answer;
import com.smarttest.quizservice.dao.entities.Question;
import com.smarttest.quizservice.dao.entities.Quiz;
import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dto.quiz.GeneratedQuizAnswerDto;
import com.smarttest.quizservice.dto.quiz.GeneratedQuizQuestionDto;
import com.smarttest.quizservice.dto.quiz.request.AnswerRequest;
import com.smarttest.quizservice.dto.quiz.request.QuestionRequest;
import com.smarttest.quizservice.dto.quiz.request.QuizRequest;
import com.smarttest.quizservice.dto.quiz.response.PassingAnswerResponse;
import com.smarttest.quizservice.dto.quiz.response.PassingQuestionResponse;
import com.smarttest.quizservice.dto.quiz.response.PassingQuizResponse;
import com.smarttest.quizservice.dto.quiz.response.QuizInfoResponse;
import com.smarttest.quizservice.yandex.dto.GeneratedAnswer;
import com.smarttest.quizservice.yandex.dto.GeneratedQuestion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface QuizMapper {

    @Mapping(target = "questionsNumber", expression = "java(entity.getQuestions().size())")
    QuizInfoResponse toQuizInfoResponse(Quiz entity);

    List<QuizInfoResponse> toQuizInfoResponseList(List<Quiz> entities);

    @Mapping(target = "id", ignore = true)
    GeneratedQuizAnswerDto toAnswerResponse(GeneratedAnswer generated);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "question")
    @Mapping(target = "type", expression = "java(com.smarttest.quizservice.dao.entities.QuestionType.SINGLE_ANSWER)")
    @Mapping(target = "answers", source = "answer")
    GeneratedQuizQuestionDto toQuestionResponse(GeneratedQuestion generated);

    List<GeneratedQuizQuestionDto> toQuestionResponseList(List<GeneratedQuestion> generatedList);

    @AfterMapping
    default void afterMapping(@MappingTarget Quiz quiz) {
        List<Question> questions = quiz.getQuestions();

        if (questions != null && !questions.isEmpty()) {
            questions.forEach(question -> question.setQuiz(quiz));
        }
    }

    @Mapping(target = "isEditable", defaultValue = "true")
    @Mapping(target = "creator", source = "user")
    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "theme", source = "request.theme")
    @Mapping(target = "questions", source = "request.questions")
    Quiz toQuizEntity(QuizRequest request, User user);

    @AfterMapping
    default void afterMapping(@MappingTarget Question question) {
        List<Answer> answers = question.getAnswers();

        if (answers != null && !answers.isEmpty())  {
            answers.forEach(answer -> answer.setQuestion(question));
        }
    }

    @Mapping(target = "quiz", ignore = true)
    Question toQuestionEntity(QuestionRequest request);

    @Mapping(target = "question", ignore = true)
    @Mapping(target = "isRight", defaultValue = "false")
    Answer toAnswerEntity(AnswerRequest request);

    PassingQuizResponse toPassingQuizResponse(Quiz entity);

    PassingQuestionResponse toPassingQuestionResponse(Question entity);

    PassingAnswerResponse toPassingAnswerResponse(Answer entity);
}
