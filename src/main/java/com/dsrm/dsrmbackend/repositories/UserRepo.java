package com.dsrm.dsrmbackend.repositories;

import com.dsrm.dsrmbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String>{
    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);
    Optional<User> findByEmail(String email);

}