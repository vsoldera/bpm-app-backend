package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DependencyRepository extends JpaRepository<Dependency, Integer> {

    //List<Dependency> findAllByUserUuid(Iterable<Integer> integers);

    @Query(value = "SELECT * FROM dependency d WHERE d.contact_uuid = ?1",
    nativeQuery = true)
    List<Dependency> returnAllUserUuid(String contactUuid);

    @Query(value = "SELECT * FROM dependency d WHERE d.user_uuid = ?1",
            nativeQuery = true)
    List<Dependency> returnAllContactUuid(String userUuid);

    Boolean existsByUserUuidAndContactUuid(String userUuid, String contactUuid);

    Dependency findByUserUuid(String userUuid);
}
