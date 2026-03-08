package com.lms.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByName(String name);

}
