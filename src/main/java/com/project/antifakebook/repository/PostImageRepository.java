package com.project.antifakebook.repository;

import com.project.antifakebook.entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImageEntity,Long> {
    List<PostImageEntity> findByPostId(Long postId);
}
