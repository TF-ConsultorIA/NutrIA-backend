package com.nutria.nutria_api.auth.repository;

import com.nutria.nutria_api.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
