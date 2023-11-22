package com.project.antifakebook.repository;

import com.project.antifakebook.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    @Query(value ="select * from message m where " +
            "m.conservation_id = :conversationId " +
            "and is_read = 0 " +
            "order by m.created_date desc limit 1 " ,nativeQuery = true)
    MessageEntity findLastMessage(Long conversationId);
    @Query(value = "select count(distinct m.conservation_id) from message m where m.from_user_id = :userId and m.is_read = 0",nativeQuery = true)
    Integer getNewNumMessage(@Param("userId") Long userId);
}
