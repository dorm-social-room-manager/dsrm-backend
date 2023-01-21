package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.Role;
import com.dsrm.dsrmbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepo extends JpaRepository<User,Long>{
    Page<User> findUserByRoles(Role roles, Pageable pageable);
}