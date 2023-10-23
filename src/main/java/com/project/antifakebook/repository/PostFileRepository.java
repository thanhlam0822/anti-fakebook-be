package com.project.antifakebook.repository;

import com.project.antifakebook.entity.PostFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFileRepository extends JpaRepository<PostFileEntity,Long> {
}
