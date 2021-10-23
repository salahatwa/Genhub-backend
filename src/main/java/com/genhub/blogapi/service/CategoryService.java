package com.genhub.blogapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.genhub.blogapi.exception.UnauthorizedException;
import com.genhub.blogapi.model.Category;
import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.security.UserPrincipal;

public interface CategoryService {

	Page<Category> getAllCategories(int page, int size);

	ResponseEntity<Category> getCategory(Long id);

	ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

	ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
			throws UnauthorizedException;

	ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;

	List<Category> getAllCategoriesList(UserPrincipal currentUser);

}
