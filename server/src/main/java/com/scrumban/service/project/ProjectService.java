package com.scrumban.service.project;

import com.scrumban.model.domain.Project;

import java.util.Optional;

public interface ProjectService {

    Project saveProject(Project newProject, String userEmail);

    Iterable<Project> findAllProjects(String userEmail);

    Optional<Project> getProject(String projectIdentifier, String userEmail);

    Project updateProject(Project project, String userEmail);

    void deleteProject(String projectIdentifier, String userEmail);
}
