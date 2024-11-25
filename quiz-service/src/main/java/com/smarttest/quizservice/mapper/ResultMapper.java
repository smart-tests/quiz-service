package com.smarttest.quizservice.mapper;

import com.smarttest.quizservice.dao.entities.Quiz;
import com.smarttest.quizservice.dao.entities.Result;
import com.smarttest.quizservice.dao.entities.User;
import com.smarttest.quizservice.dto.result.response.ResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {
        QuizMapper.class,
        UserMapper.class
})
public interface ResultMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    Result toResultEntity(User user, Quiz quiz, Integer rightAnswersNumber);

    ResultResponse toResultResponse(Result entity);

    List<ResultResponse> toResultResponseList(List<Result> entities);
}
