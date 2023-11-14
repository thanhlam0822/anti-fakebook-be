package com.project.antifakebook.repository;

import com.project.antifakebook.entity.SavedSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SavedSearchRepository extends JpaRepository<SavedSearchEntity,Long> {
    @Query(value = "with save_search as ("
            + "    select *,rank() over (order by created_date desc ) as search_index from saved_search "
            + "      where user_id = :userId"
            + ") select * from save_search where search_index >= :index limit :count",nativeQuery = true)
    List<SavedSearchEntity> findByUserId(@Param("userId") Long userId,
                                         @Param("index") Integer index,
                                         @Param("count") Integer count);
}
