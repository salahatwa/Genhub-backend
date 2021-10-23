package com.genhub.blogapi.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.genhub.blogapi.exception.ResourceNotFoundException;
import com.genhub.blogapi.exception.UnauthorizedException;
import com.genhub.blogapi.model.Category;
import com.genhub.blogapi.model.role.RoleName;
import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.repository.CategoryRepository;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.service.CategoryService;
import com.genhub.blogapi.utils.AppUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Page<Category> getAllCategories(int page, int size) {
		AppUtils.validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

		Page<Category> categories = categoryRepository.findAll(pageable);

		List<Category> content = categories.getNumberOfElements() == 0 ? Collections.emptyList()
				: categories.getContent();

		for (Category category : content) {
			String parentName = Objects.nonNull(category.getParent()) ? category.getParent().getName() : "";
			System.out.println(category.getName() + "::" + category.getId() + "::" + parentName);
		}

		return categories;
	}

	@Override
	public ResponseEntity<Category> getCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser) {
		category = categoryRepository.save(category);
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		if (category.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			//clear existing children list so that they are removed from database
//			category.getParent().getChildren().clear();
			//add the new children list created above to the existing list
//			parent.getChildren.addAll(children);
			category.setName(newCategory.getName());
			category.setParent(newCategory.getParent());
			Category updatedCategory = categoryRepository.save(category);
			return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
		}

		throw new UnauthorizedException("You don't have permission to edit this category");
	}

	@Override
	public ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		if (category.getCreatedBy().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			categoryRepository.deleteById(id);
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"),
					HttpStatus.OK);
		}
		throw new UnauthorizedException("You don't have permission to delete this category");
	}

	public void recursiveCategoryTree(UserPrincipal currentUser) {
		List<Category> rootCategoryList = categoryRepository.findByCreatedByAndParentIsNull(currentUser.getId());
		for (Category cat : rootCategoryList) {
			recursiveTree(cat);
		}
	}

	public void recursiveTree(Category cat) {
		System.out.println(cat.getName());
		if (cat.getChildren().size() > 0) {
			for (Category c : cat.getChildren()) {
				recursiveTree(c);
			}
		}
	}

	@Override
	public List<Category> getAllCategoriesList(UserPrincipal currentUser) {
		return categoryRepository.findByCreatedBy(currentUser.getId());
	}
}
