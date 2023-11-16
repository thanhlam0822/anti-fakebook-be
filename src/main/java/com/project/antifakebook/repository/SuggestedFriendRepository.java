package com.project.antifakebook.repository;

import com.project.antifakebook.entity.SuggestedFriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuggestedFriendRepository extends JpaRepository<SuggestedFriendEntity,Long> {
    @Query(value = "with suggested_request as ( " +
            "    select *,rank() over (order by created_date desc ) as request_index from suggested_friend r " +
            "    where r.user_id = :userId " +
            ")select * from suggested_request where request_index >= :index limit :count" ,nativeQuery = true)
    List<SuggestedFriendEntity> getListSuggestedFriends(@Param("userId") Long userId,
                                                        @Param("index") Integer index,
                                                        @Param("count") Integer count);
}
