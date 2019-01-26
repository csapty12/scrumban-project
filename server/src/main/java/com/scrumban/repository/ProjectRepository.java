package com.scrumban.repository;

import com.scrumban.model.project.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    @Override
    Iterable<ProjectEntity> findAllById(Iterable<Long> iterable);

    Optional<ProjectEntity> findProjectByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<ProjectEntity> findAll();

    @Override
    void delete(ProjectEntity projectEntity);

    ProjectEntity findByProjectIdentifier(String projectIdentifier);

}