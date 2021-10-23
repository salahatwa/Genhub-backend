package com.genhub.blogapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genhub.blogapi.exception.UnauthorizedException;
import com.genhub.blogapi.model.Category;
import com.genhub.blogapi.model.dto.CategoryDto;
import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.security.CurrentUser;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.service.CategoryService;
import com.genhub.blogapi.utils.AppConstants;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Category> getAllCategories(
			@RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		return categoryService.getAllCategories(page, size);
	}

	
	@GetMapping("/list")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Category> getAllCategoriesList(@CurrentUser UserPrincipal currentUser) {
		return categoryService.getAllCategoriesList(currentUser);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category,
			@CurrentUser UserPrincipal currentUser) {

		return categoryService.addCategory(category, currentUser);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
		return categoryService.getCategory(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Long id,
			@Valid @RequestBody Category category, @CurrentUser UserPrincipal currentUser)
			throws UnauthorizedException {
		return categoryService.updateCategory(id, category, currentUser);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
			@CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
		return categoryService.deleteCategory(id, currentUser);
	}

}
