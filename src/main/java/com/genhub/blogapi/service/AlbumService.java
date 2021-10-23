package com.genhub.blogapi.service;

import com.genhub.blogapi.payload.AlbumResponse;
import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.PagedResponse;
import com.genhub.blogapi.payload.request.AlbumRequest;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.model.Album;

import org.springframework.http.ResponseEntity;

public interface AlbumService {

	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

	ResponseEntity<Album> getAlbum(Long id);

	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

	ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

	PagedResponse<Album> getUserAlbums(String username, int page, int size);

}
