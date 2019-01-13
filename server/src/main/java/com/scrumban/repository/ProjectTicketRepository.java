package com.scrumban.repository;

import com.scrumban.model.project.ProjectTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectTicketRepository  extends CrudRepository<ProjectTicket, Long> {
    List<ProjectTicket> findProjectTicketsByProjectIdentifier(String projectIdentifier);
}
