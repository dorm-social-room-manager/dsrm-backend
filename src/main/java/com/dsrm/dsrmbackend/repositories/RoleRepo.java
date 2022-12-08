package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.tables.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
