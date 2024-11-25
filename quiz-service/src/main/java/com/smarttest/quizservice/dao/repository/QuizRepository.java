package com.smarttest.quizservice.dao.repository;

import com.smarttest.quizservice.dao.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> findAllByCreatorId(UUID id);
}
