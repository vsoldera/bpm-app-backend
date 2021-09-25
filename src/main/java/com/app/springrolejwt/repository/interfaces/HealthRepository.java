package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.Dependency;
import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.vo.userVos.MonitoredVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HealthRepository  extends JpaRepository<Health, Long> {

    Optional<Health> findByUuid(String uuid);

    Boolean existsByUuid(String uuid);

    @Query(value = "SELECT * FROM health h WHERE h.uuid = ?1",
            nativeQuery = true)
    List<Health> returnAllUserUuid(String uuid);
}
