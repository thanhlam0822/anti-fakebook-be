package com.project.antifakebook.repository;

import com.project.antifakebook.dto.block_user.GetListBlocksResponseDto;
import com.project.antifakebook.entity.BlockUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockUserRepository extends JpaRepository<BlockUserEntity,Long> {
    Boolean existsByUserBlockedIdAndUserPostId(Long userBlockedId,Long userId);
    @Query(value ="with blocks_list as (\n" +
            "    select *,rank() over (order by created_date desc ) as block_index from block_user b\n" +
            "    where b.user_post_id = :userId\n" +
            ") select a.id as id,a.name as name ,a.avatar_link as avatar " +
            "  from blocks_list b join account a on b.user_blocked_id = a.id  where b.block_index >= :index limit :count " ,nativeQuery = true)
    List<GetListBlocksResponseDto> getListBlocks(@Param("userId") Long userId,
                                                 @Param("index") Integer index,
                                                 @Param("count") Integer count);
}
