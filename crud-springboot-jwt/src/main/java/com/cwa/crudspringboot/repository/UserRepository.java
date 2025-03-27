package com.cwa.crudspringboot.repository;

import com.cwa.crudspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}
