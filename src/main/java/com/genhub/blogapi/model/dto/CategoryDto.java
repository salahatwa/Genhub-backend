package com.genhub.blogapi.model.dto;

import com.genhub.blogapi.model.Category;

import lombok.Data;

@Data
public class CategoryDto {

	private Long id;

	private String name;

	private Category parent;
}
