package com.scrumban.repository.entity;

import com.scrumban.model.entity.SwimLaneEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SwimLaneEntityRepository extends CrudRepository<SwimLaneEntity, Long> {
    Optional<SwimLaneEntity> findById(int swimLaneId);
    Optional<SwimLaneEntity> findByName(String swimLaneName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_swimlane where project_id = ?1  and swimlane_id = ?2", nativeQuery = true)
    void deleteSwimLaneEntityByName(Long projectId, Integer swimLaneId );
}
