package com.scrumban.repository;

import com.scrumban.model.project.ProjectTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTicket, Long> {
//    List<ProjectTicket> findAllByProjectIdentifier(String projectIdentifier);
//    ProjectTicket findProjectTaskByProjectSequence(String projectSequence);
}