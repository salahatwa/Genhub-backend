package com.genhub.blogapi.service;

import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.PagedResponse;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.model.Todo;

public interface TodoService {

	Todo completeTodo(Long id, UserPrincipal currentUser);

	Todo unCompleteTodo(Long id, UserPrincipal currentUser);

	PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

	Todo addTodo(Todo todo, UserPrincipal currentUser);

	Todo getTodo(Long id, UserPrincipal currentUser);

	Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

	ApiResponse deleteTodo(Long id, UserPrincipal currentUser);

}
