package com.project.antifakebook.repository;

import com.project.antifakebook.entity.ReactEntity;
import com.project.antifakebook.enums.ReactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReactRepository extends JpaRepository<ReactEntity,Long> {

    @Query("select count(r) from ReactEntity r where r.postId = ?1 and r.reactType = ?2")
    Integer countReactOfPost(Long postId, ReactType reactType);
    @Query("select count(r) from ReactEntity  r where r.userId = ?1 and r.postId = ?2")
    Integer isRate(Long userId,Long postId);
    ReactEntity findByPostIdAndAndReactTypeAndUserId(Long postId,ReactType reactType,Long userId);
    @Query("select count(r) from ReactEntity r where r.postId = ?1")
    Integer totalFeelOfPost(Long postId);
    Boolean existsByPostIdAndUserId(Long postId,Long userId);
}
