package com.scrumban.repository;

import com.scrumban.model.project.entity.ProjectTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTicket, Long> {
//    List<ProjectTicket> findAllByProjectIdentifier(String projectIdentifier);
//    ProjectTicket findProjectTaskByProjectSequence(String projectSequence);
}