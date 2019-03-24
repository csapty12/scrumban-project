package com.scrumban.repository.domain;

import com.scrumban.model.domain.Project;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.repository.entity.ProjectEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private ProjectEntityRepository projectEntityRepository;

    public ProjectRepositoryImpl(ProjectEntityRepository projectEntityRepository) {
        this.projectEntityRepository = projectEntityRepository;
    }

    @Override
    public Optional<Project> findProjectEntityByProjectIdentifier(String projectIdentifier) {

        Optional<ProjectEntity> projectEntity = projectEntityRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
        if(projectEntity.isPresent()){
            return Optional.of(Project.from(projectEntity.get()));
        }
       return Optional.empty();
    }

    @Override
    public Project save(Project newProject) {
        ProjectEntity projectEntity = ProjectEntity.from(newProject);
        ProjectEntity savedProject = projectEntityRepository.save(projectEntity);
        return Project.from(savedProject);
    }
}
