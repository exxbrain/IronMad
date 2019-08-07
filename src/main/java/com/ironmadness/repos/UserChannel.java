package com.ironmadness.repos;

import com.ironmadness.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChannel extends JpaRepository<Channel, Long> {
    Channel findByNameAndAndText(String name, String text);
}