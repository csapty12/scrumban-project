package com.scrumban.repository;

import com.scrumban.model.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    List<ProjectTask> findAllByProjectIdentifier(String projectIdentifier);
    ProjectTask findProjectTaskByProjectSequence(String projectSequence);
}
