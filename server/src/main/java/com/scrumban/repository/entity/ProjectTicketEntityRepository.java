package com.scrumban.repository.entity;

import com.scrumban.model.entity.ProjectTicketEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProjectTicketEntityRepository extends CrudRepository<ProjectTicketEntity, Long> {


    @Modifying
    @Transactional
    @Query("delete from ProjectTicketEntity p where id = ?1")
    void deleteProjectTicket(long id);

    ProjectTicketEntity findByProjectSequence(String projectSequence);




}
