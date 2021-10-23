package com.genhub.blogapi.repository;

import com.genhub.blogapi.model.role.Role;
import com.genhub.blogapi.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
