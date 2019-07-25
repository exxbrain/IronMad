package com.ironmadness.repos;

import com.ironmadness.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_Channel extends JpaRepository<Channel, Long> {
    Channel findByNameAndAndText(String name, String text);
}
