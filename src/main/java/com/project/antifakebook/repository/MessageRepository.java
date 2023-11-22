package com.project.antifakebook.repository;

import com.project.antifakebook.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    @Query(value ="select * from message m where " +
            "m.conservation_id = :conversationId " +
            "and is_read = 0 " +
            "order by m.created_date desc limit 1 " ,nativeQuery = true)
    MessageEntity findLastMessage(Long conversationId);
    @Query(value = "select count(distinct m.conservation_id) from message m where m.from_user_id = :userId and m.is_read = 0",nativeQuery = true)
    Integer getNewNumMessage(@Param("userId") Long userId);
    @Query(value = "with messages as(\n" +
            "    select * ,rank() over (order by created_date) as message_index from message m where\n" +
            "        (m.to_user_id = :partnerId or m.from_user_id = :partnerId )\n" +
            "        and m.conversation_id = :conversationId\n" +
            ") select * from messages m2 where m2.message_index >= :index limit :count",nativeQuery = true)
    List<MessageEntity> findByToUserIdAndConversationId(@Param("partnerId") Long partnerId,
                                                        @Param("conversationId") Long conversationId,
                                                        @Param("index") Integer index,
                                                        @Param("count") Integer count);
    @Modifying
    @Transactional
    @Query(value = "update message m set m.is_read = 1 where m.is_read = 0 \n" +
            "                                     and (m.to_user_id = :partnerId or m.from_user_id = :partnerId ) \n" +
            "                                     and m.conversation_id = :conversationId",nativeQuery = true)
    void setReadMessage(@Param("partnerId") Long partnerId,
                        @Param("conversationId") Long conversationId);
    List<MessageEntity> findByConversationId(Long conversationId);
}
