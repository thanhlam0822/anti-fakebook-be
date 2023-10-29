package com.project.antifakebook.repository;

import com.project.antifakebook.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query("select c from CategoryEntity c join PostCategoryEntity p on c.id = p.categoryId where p.postId = ?1")
    List<CategoryEntity> getCategoryOfPost(Long postId);
}
