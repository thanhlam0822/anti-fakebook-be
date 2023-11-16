package com.project.antifakebook.repository;

import com.project.antifakebook.entity.RequestFriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestFriendRepository extends JpaRepository<RequestFriendEntity,Long> {
    @Query(value = "with requested_friends as (\n" +
            "    select *,rank() over (order by created_date desc ) as request_index from request_friend where user_id = :userId and is_accept = false" +
            ") select * from requested_friends where request_index >= :index limit :count" ,nativeQuery = true)
    List<RequestFriendEntity> getRequestFriendByUserId(Long userId,Integer index,Integer count);
    @Query("select count(r) from RequestFriendEntity r where r.userId = ?1 and r.isAccept = false ")
    Integer getTotalRequestOfUser(Long userId);
    RequestFriendEntity findRequestFriendEntitiesByUserIdAndFriendIdAndIsAcceptFalse(Long userId,Long fiendId);
    @Query("select count(r) from RequestFriendEntity r where r.userId = ?1 and r.isAccept = false ")
    Integer countRequestFriendNumber(Long currentUserId);

}
