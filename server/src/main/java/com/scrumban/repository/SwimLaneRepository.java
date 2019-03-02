package com.scrumban.repository;

import com.scrumban.model.project.entity.SwimLaneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwimLaneRepository extends CrudRepository<SwimLaneEntity, Long> {

    Optional<SwimLaneEntity> findByName(String swimLaneName);
}
