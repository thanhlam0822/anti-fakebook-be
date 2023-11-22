package com.project.antifakebook.repository;

import com.project.antifakebook.entity.ConservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConservationRepository extends JpaRepository<ConservationEntity,Long> {
    @Query(value = "with conversations as (\n" +
            "    select *," +
            "           rank() over (order by created_date desc ) as conversation_index from conservation c where c.from_user_id = :userId\n" +
            ") select * from conversations c2 where c2.conversation_index >= :index limit :count" ,nativeQuery = true)
    List<ConservationEntity> findByFromUserId(@Param("userId") Long currentUserId,
                                              @Param("index") Integer index,
                                              @Param("count") Integer count);
}
