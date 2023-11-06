package com.project.antifakebook.repository;

import com.project.antifakebook.dto.rate.GetRateResponseDto;
import com.project.antifakebook.entity.RateEntity;
import com.project.antifakebook.enums.RateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRateRepository extends JpaRepository<RateEntity,Long> {
    @Query("select count(r) from RateEntity r where r.postId = ?1 and r.rateType = ?2")
    Integer countRateOfPost(Long postId, RateType rateType);
    @Query("select count(r) from RateEntity r where r.userId = ?1 and r.postId = ?2")
    Integer isMark(Long userId,Long postId);
    @Query(value = "WITH ranked_mark AS "
          +  "(SELECT *,"
          +  "           ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY created_date) AS mark_index"
          +  " FROM rate r where parent_id is null"
          +  ")"
          +  "SELECT r.id as id, r.content as markContent,r.rate_type as typeOfMark,r.post_id as postId,r.user_id as userId "
          +  "FROM ranked_mark r WHERE r.post_id = :postId and r.user_id not in (select user_post_id from block_user where user_blocked_id = :currentUserId " +
            "                                                                               union" +
            "                                                                               select user_blocked_id from block_user where user_post_id = :currentUserId)  "
          +  " and r.mark_index >= :markIndex limit :count",nativeQuery = true)
    List<GetRateResponseDto> findByPostId(@Param("postId") Long postId,
                                          @Param("markIndex") Integer markIndex,
                                          @Param("count") Integer count,
                                          @Param("currentUserId") Long currentUserId);
    @Query(value = "select r.content as markContent,r.created_date as createdDate,r.user_id as userId from rate r where "
           + " r.post_id = :postId "
           + " and r.parent_id = :parentId "
           + " and r.user_id not in (select user_post_id from block_user where user_post_id= :currentUserId union "
           + " select user_blocked_id from block_user where user_post_id = :currentUserId )",nativeQuery = true)
    List<GetRateResponseDto> getByPostIdAndParentId(@Param("postId") Long postId,
                                            @Param("parentId") Long parentId,
                                            @Param("currentUserId") Long currentUserId);
}
