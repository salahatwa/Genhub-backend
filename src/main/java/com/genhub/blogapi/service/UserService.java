package com.genhub.blogapi.service;

import java.util.List;

import com.genhub.blogapi.model.role.Role;
import com.genhub.blogapi.model.user.User;
import com.genhub.blogapi.payload.ApiResponse;
import com.genhub.blogapi.payload.InfoRequest;
import com.genhub.blogapi.payload.UserIdentityAvailability;
import com.genhub.blogapi.payload.UserProfile;
import com.genhub.blogapi.payload.UserSummary;
import com.genhub.blogapi.security.UserPrincipal;

public interface UserService {

	UserSummary getCurrentUser(UserPrincipal currentUser,String accessToken);

	UserIdentityAvailability checkUsernameAvailability(String username);

	UserIdentityAvailability checkEmailAvailability(String email);

	UserProfile getUserProfile(String username);

	User addUser(User user);

	User updateUser(User newUser, String username, UserPrincipal currentUser);

	ApiResponse deleteUser(String username, UserPrincipal currentUser);

	ApiResponse giveAdmin(String username);

	ApiResponse removeAdmin(String username);

	UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);
	
	List<Role> getAllRoles();

}