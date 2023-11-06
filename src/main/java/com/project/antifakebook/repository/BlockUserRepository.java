package com.project.antifakebook.repository;

import com.project.antifakebook.entity.BlockUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockUserRepository extends JpaRepository<BlockUserEntity,Long> {
    Boolean existsByUserBlockedIdAndUserPostId(Long userBlockedId,Long userId);
}
