package com.project.antifakebook.repository;

import com.project.antifakebook.entity.OldVersionOfPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldVersionOfPostRepository extends JpaRepository<OldVersionOfPostEntity,Long> {
    @Query("select o.oldPostId from OldVersionOfPostEntity o where o.postId = ?1 ")
    List<Long> getOldVersionOfCurrentPost(Long postId);
}
