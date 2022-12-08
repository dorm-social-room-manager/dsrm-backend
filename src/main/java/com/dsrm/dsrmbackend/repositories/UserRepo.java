package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.tables.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {}