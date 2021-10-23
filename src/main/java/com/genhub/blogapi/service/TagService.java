package com.genhub.blogapi.service;

import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.PagedResponse;
import com.genhub.blogapi.security.UserPrincipal;
import com.genhub.blogapi.model.Tag;

public interface TagService {

	PagedResponse<Tag> getAllTags(int page, int size);

	Tag getTag(Long id);

	Tag addTag(Tag tag, UserPrincipal currentUser);

	Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser);

	ApiResponse deleteTag(Long id, UserPrincipal currentUser);

}
