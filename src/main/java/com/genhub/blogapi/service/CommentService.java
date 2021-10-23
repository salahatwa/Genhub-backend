package com.genhub.blogapi.service;

import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.CommentRequest;
import com.genhub.blogapi.payload.PagedResponse;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.model.Comment;

public interface CommentService {

	PagedResponse<Comment> getAllComments(Long postId, int page, int size);

	Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

	Comment getComment(Long postId, Long id);

	Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

	ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}
