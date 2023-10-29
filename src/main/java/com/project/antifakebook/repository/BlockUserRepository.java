package com.project.antifakebook.repository;

import com.project.antifakebook.entity.BlocKUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockUserRepository extends JpaRepository<BlocKUserEntity,Long> {
    Boolean existsByUserBlockedIdAndUserPostId(Long userBlockedId,Long userId);
}
