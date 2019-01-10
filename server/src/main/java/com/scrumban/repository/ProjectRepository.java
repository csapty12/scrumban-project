package com.scrumban.repository;

import com.scrumban.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);

    Project findProjectByProjectIdentifier(String projectIdentifier);

    @Override
    Iterable<Project> findAll();

    @Override
    void delete(Project project);
}