package com.project.antifakebook.repository;

import com.project.antifakebook.entity.NotificationSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettingsEntity,Long> {
    NotificationSettingsEntity findByUserId(Long userId);
}
