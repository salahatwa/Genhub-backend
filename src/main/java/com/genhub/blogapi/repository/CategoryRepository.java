package com.genhub.blogapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genhub.blogapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByCreatedByAndParentIsNull(long createdBy);
	List<Category> findByCreatedBy(long createdBy);
}
