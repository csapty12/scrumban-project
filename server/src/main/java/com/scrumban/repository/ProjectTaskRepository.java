package com.scrumban.repository;

import com.scrumban.model.project.ProjectTickets;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTickets, Long> {
    List<ProjectTickets> findAllByProjectIdentifier(String projectIdentifier);
    ProjectTickets findProjectTaskByProjectSequence(String projectSequence);
}