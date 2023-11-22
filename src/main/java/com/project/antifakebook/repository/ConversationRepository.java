package com.project.antifakebook.repository;

import com.project.antifakebook.entity.ConversationEntity;
import com.project.antifakebook.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity,Long> {
    @Query(value = "with conversations as (\n" +
            "    select *," +
            "           rank() over (order by created_date desc ) as conversation_index from conversation c where c.from_user_id = :userId\n" +
            ") select * from conversations c2 where c2.conversation_index >= :index limit :count" ,nativeQuery = true)
    List<ConversationEntity> findByFromUserId(@Param("userId") Long currentUserId,
                                              @Param("index") Integer index,
                                              @Param("count") Integer count);
    @Modifying
    @Transactional
    @Query(value = "delete from message m where m.conversation_id = :conversationId ",nativeQuery = true)
    void deleteAllByConversationId(@Param("conversationId") Long conversationId);

}
