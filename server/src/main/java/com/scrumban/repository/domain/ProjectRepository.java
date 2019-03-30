package com.scrumban.repository.domain;

import com.scrumban.model.domain.Project;
import com.scrumban.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> findProjectEntityByProjectIdentifier(String projectIdentifier);
    Project save(Project newProject);
    List<Project> findAllByUser(User user);
    void delete(Project project);
}
