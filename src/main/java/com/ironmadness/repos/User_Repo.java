package com.ironmadness.repos;

import com.ironmadness.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_Repo extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
