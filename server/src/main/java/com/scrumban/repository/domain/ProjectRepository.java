package com.scrumban.repository.domain;

import com.scrumban.model.entity.ProjectEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    Optional<ProjectEntity> findProjectEntityByProjectIdentifier(String projectIdentifier);
}
