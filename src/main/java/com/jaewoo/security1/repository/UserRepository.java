package com.jaewoo.security1.repository;

import com.jaewoo.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
