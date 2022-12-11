package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
