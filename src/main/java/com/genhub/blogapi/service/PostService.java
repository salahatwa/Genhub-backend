package com.genhub.blogapi.service;

import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.PagedResponse;
import com.genhub.blogapi.payload.PostRequest;
import com.genhub.blogapi.payload.PostResponse;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.model.Post;

public interface PostService {

	PagedResponse<Post> getAllPosts(int page, int size);

	PagedResponse<Post> getPostsByCreatedBy(String username, int page, int size);

	PagedResponse<Post> getPostsByCategory(Long id, int page, int size);

	PagedResponse<Post> getPostsByTag(Long id, int page, int size);

	Post updatePost(Long id, PostRequest newPostRequest, UserPrincipal currentUser);

	ApiResponse deletePost(Long id, UserPrincipal currentUser);

	PostResponse addPost(PostRequest postRequest, UserPrincipal currentUser);

	Post getPost(Long id);

}
