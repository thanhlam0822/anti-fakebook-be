package com.project.antifakebook.repository;

import com.project.antifakebook.entity.RateEntity;
import com.project.antifakebook.enums.RateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRateRepository extends JpaRepository<RateEntity,Long> {
    @Query("select count(r) from RateEntity r where r.postId = ?1 and r.rateType = ?2")
    Integer countRateOfPost(Long postId, RateType rateType);
    @Query("select count(r) from RateEntity r where r.userId = ?1 and r.postId = ?2")
    Integer isMark(Long userId,Long postId);
}
