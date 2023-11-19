package com.project.antifakebook.repository;

import com.project.antifakebook.dto.notifications.GetNotificationResponseDto;
import com.project.antifakebook.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    @Query(value = "with notifications as (\n" +
            "    select *,rank() over (order by created_date desc) as notification_index from notification n\n" +
            "                                                                            where n.user_id = :userId\n" +
            ") select type as type, object_id as objectId, title as title,\n" +
            "         id as notificationId,\n" +
            "         created_date as created,\n" +
            "         avatar as avatar,\n" +
            "         group_type as groupType,\n" +
            "         is_read as isRead\n" +
            "  from notifications where notification_index >= :index limit :count" ,nativeQuery = true)
    List<GetNotificationResponseDto> getNotifications(@Param("userId") Long userId,
                                                      @Param("index") Integer index,
                                                      @Param("count") Integer count);
    @Query(value = "select count(*) from notification n\n" +
            "           where n.is_read = 0\n" +
            "                  and n.created_date >= (select max(n2.created_date)\n" +
            "                                      from notification n2 where n2.is_read = 1 and n2.user_id = :userId)\n" +
            "                  and n.user_id = :userId" ,nativeQuery = true)
    Integer countBadge(@Param("userId") Long userId);
    @Query(value = "select max(n2.created_date)\n" +
            "                                      from notification n2 where n2.is_read = 1 and n2.user_id = :userId",nativeQuery = true)
    Date lastUpdate(@Param("userId") Long userId);
    NotificationEntity findByIdAndUserIdAndIsRead(Long notificationId,Long userId,Integer isRead);

}
