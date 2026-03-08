package com.lms.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.library.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    public boolean existsByName(String name);

}
