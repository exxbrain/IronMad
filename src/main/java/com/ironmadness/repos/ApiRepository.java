package com.ironmadness.repos;

import com.ironmadness.domain.Api;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api, Long> {
    Api findByUserId(Integer api);
}
