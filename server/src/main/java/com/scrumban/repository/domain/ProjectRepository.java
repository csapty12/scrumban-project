package com.scrumban.repository.domain;

import com.scrumban.model.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> findProjectEntityByProjectIdentifier(String projectIdentifier);

    Project save(Project newProject);
}
