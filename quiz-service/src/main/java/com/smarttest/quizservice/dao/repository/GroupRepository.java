package com.smarttest.quizservice.dao.repository;

import com.smarttest.quizservice.dao.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

    List<Group> findAllByOwnerId(UUID id);
}
