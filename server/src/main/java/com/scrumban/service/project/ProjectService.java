package com.scrumban.service.project;

import com.scrumban.model.domain.Project;

public interface ProjectService {

    Project saveProject(Project newProject, String userEmail);
}
