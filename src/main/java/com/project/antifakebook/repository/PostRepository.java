package com.project.antifakebook.repository;

import com.project.antifakebook.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    @Query(value = "with searched_post as ("
          +  "    select *,rank() over (order by created_date desc ) as post_index from post p  "
          +  "where p.described like concat('%',:keyword,'%') or p.name like concat('%',:keyword,'%') "
          +  ") "
          +  "select * from searched_post where post_index >= :index limit :count",nativeQuery = true)
    List<PostEntity> searchPosts(@Param("keyword") String keyword,
                                 @Param("index") Integer index,
                                 @Param("count") Integer count);
    @Query(value ="with posts as (\n" +
            "    select id, rank() over (order by created_date desc ) as post_index from post p\n" +
            "    where p.is_discount = :inCampaign\n" +
            "      and p.campaign_id = :campaignId\n" +
            "      and (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude)))) <= 10\n" +
            ")\n" +
            "select * from posts where post_index >= :index and id >= :lastId limit :count" ,nativeQuery = true)
    List<Long> getListIdsOfPost(@Param("inCampaign") Boolean inCampaign,
                                @Param("campaignId") Long campaignId,
                                @Param("latitude") Double latitude,
                                @Param("longitude") Double longitude,
                                @Param("lastId") Long lastId,
                                @Param("index") Integer index,
                                @Param("count") Integer count);
    List<PostEntity> findByIdIn(List<Long> postIds);
}
