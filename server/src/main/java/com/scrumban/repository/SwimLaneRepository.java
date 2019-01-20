package com.scrumban.repository;

import com.scrumban.model.project.entity.SwimLaneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwimLaneRepository extends CrudRepository<SwimLaneEntity, Long> {
        SwimLaneEntity findByName(String swimLaneName);

}
