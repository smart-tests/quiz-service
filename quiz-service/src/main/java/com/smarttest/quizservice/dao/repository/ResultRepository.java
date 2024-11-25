package com.smarttest.quizservice.dao.repository;

import com.smarttest.quizservice.dao.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {

    List<Result> findAllByUserId(UUID id);

    List<Result> findAllByQuizId(UUID id);
}
