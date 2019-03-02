package com.scrumban.repository;

import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {
    @Override
    Iterable<ProjectEntity> findAllById(Iterable<Long> iterable);

    Optional<ProjectEntity> findProjectEntityByProjectIdentifier(String projectIdentifier);

    List<ProjectEntity> findAllByUser(User user);

    @Override
    void delete(ProjectEntity projectEntity);
}