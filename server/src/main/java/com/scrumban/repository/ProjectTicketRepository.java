package com.scrumban.repository;

import com.scrumban.model.project.ProjectTicket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface ProjectTicketRepository  extends CrudRepository<ProjectTicket, Long> {
    List<ProjectTicket> findProjectTicketsByProjectIdentifier(String projectIdentifier);

    @Modifying
    @Transactional
    @Query("delete from ProjectTicket p where id = ?1")
    void deleteProjectTicket(long id);
}
