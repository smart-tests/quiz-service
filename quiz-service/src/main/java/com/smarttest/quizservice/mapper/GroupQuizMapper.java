package com.smarttest.quizservice.mapper;

import com.smarttest.quizservice.dao.entities.Group;
import com.smarttest.quizservice.dao.entities.GroupQuiz;
import com.smarttest.quizservice.dao.entities.Quiz;
import com.smarttest.quizservice.dto.group.request.AddGroupQuizRequest;
import com.smarttest.quizservice.dto.group.response.GroupQuizResponse;
import com.smarttest.quizservice.dto.quiz.response.QuizForPassingInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper(uses = {
        QuizMapper.class
})
public interface GroupQuizMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startDate", expression = "java(toMidnight(request.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(toMidnight(request.getEndDate()))")
    GroupQuiz toGroupQuizEntity(AddGroupQuizRequest request, Group group, Quiz quiz);

    @Mapping(target = "groupId", source = "entity.group.id")
    GroupQuizResponse toGroupQuizResponse(GroupQuiz entity);

    List<GroupQuizResponse> toGroupQuizResponseList(List<GroupQuiz> entities);

    @Mapping(target = "id", source = "entity.quiz.id")
    @Mapping(target = "title", source = "entity.quiz.title")
    @Mapping(target = "theme", source = "entity.quiz.theme")
    @Mapping(target = "groupName", source = "entity.group.name")
    @Mapping(target = "questionsNumber", expression = "java(entity.getQuiz().getQuestions().size())")
    QuizForPassingInfo toQuizForPassingInfo(GroupQuiz entity);

    List<QuizForPassingInfo> toQuizForPassingInfoList(List<GroupQuiz> entities);

    default LocalDateTime toMidnight(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
    }
}
