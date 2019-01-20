package com.scrumban.repository;

import com.scrumban.model.project.entity.ProjectTicket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProjectTicketRepository  extends CrudRepository<ProjectTicket, Long> {
    List<ProjectTicket> findProjectTicketsByProjectIdentifier(String projectIdentifier);

    @Modifying
    @Transactional
    @Query("delete from ProjectTicket p where id = ?1")
    void deleteProjectTicket(long id);
}
