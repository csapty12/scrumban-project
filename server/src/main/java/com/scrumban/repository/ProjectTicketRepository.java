package com.scrumban.repository;

import com.scrumban.model.project.ProjectTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectTicketRepository  extends CrudRepository<ProjectTicket, Long> {
    Set<ProjectTicket> findProjectTicketsByProjectIdentifier(String projectIdentifier);
}
