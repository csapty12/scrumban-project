package com.scrumban.repository;

import com.scrumban.model.project.entity.SwimLaneEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SwimLaneRepository extends CrudRepository<SwimLaneEntity, Long> {
    Optional<SwimLaneEntity> findByName(String swimLaneName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_swimlane where project_id = ?1  and swimlane_id = ?2", nativeQuery = true)
    void deleteSwimLaneEntityByName(Long projectId, Integer swimLaneId );
}
