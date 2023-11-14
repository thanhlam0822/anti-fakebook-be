package com.project.antifakebook.repository;

import com.project.antifakebook.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity,Long> {
    @Query(value = "select count(*) from (" +
            "                         select  friend_id " +
            "                         from friend  where user_id = :currentUser" +
            "                         intersect " +
            "                         select friend_id " +
            "                         from  friend where user_id = :userFriend" +
            "                     ) as test",nativeQuery = true)
    Integer getSameFriendsAmount(@Param("currentUser") Long currentUserId,
                                 @Param("userFriend") Long friendId);
    @Query(value = "with friend as (\n" +
            "    select *,rank() over (order by created_date desc ) as friend_index from friend where user_id = :userId\n" +
            ") select * from friend where friend_index >= :index limit :count" ,nativeQuery = true)
    List<FriendEntity> getFriendEntitiesByUserId(@Param("userId") Long userId,
                                                 @Param("index") Integer index,
                                                 @Param("count") Integer count);
    Integer countFriendEntitiesByUserId(Long userId);
    FriendEntity findByUserIdAndFriendId(Long userId,Long friendId);
}
