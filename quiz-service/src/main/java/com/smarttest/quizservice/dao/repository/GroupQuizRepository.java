package com.smarttest.quizservice.dao.repository;

import com.smarttest.quizservice.dao.entities.GroupQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupQuizRepository extends JpaRepository<GroupQuiz, UUID> {

    List<GroupQuiz> findGroupsQuizzesByGroupId(UUID groupId);

    List<GroupQuiz> findGroupsQuizzesByQuizId(UUID quizId);

    @Query("select gq from GroupQuiz gq where gq.group.id = :groupId and gq.quiz.id = :quizId")
    Optional<GroupQuiz> findGroupQuizByGroupIdAndQuizId(UUID groupId, UUID quizId);

    @Query("""
            select gq from GroupQuiz gq
            join gq.group gr
            join gr.members m
            where m.id = :userId and gq.quiz.id = :quizId
            """)
    Optional<GroupQuiz> findByGroupMemberIdAndQuizId(UUID userId, UUID quizId);

    @Query("""
            select gq from GroupQuiz  gq
            join gq.group gr
            join gr.members m
            where m.id = :userId and :date >= gq.startDate and :date < gq.endDate
            """)
    List<GroupQuiz> findAllByGroupMemberIdAndCurrentDate(UUID userId, LocalDateTime date);
}
