package com.scrumban.repository;

import com.scrumban.model.project.SwimLane;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwimLaneRepository extends CrudRepository<SwimLane, Long> {

}
