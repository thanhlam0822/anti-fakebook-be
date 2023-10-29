package com.project.antifakebook.repository;

import com.project.antifakebook.entity.PostVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVideoRepository extends JpaRepository<PostVideoEntity,Long> {
    List<PostVideoEntity> findByPostId(Long postId);
}
