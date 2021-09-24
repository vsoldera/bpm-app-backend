package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.Health;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthRepository  extends JpaRepository<Health, Long> {

    Optional<Health> findByUuid(String uuid);

    Boolean existsByUuid(String uuid);
}
