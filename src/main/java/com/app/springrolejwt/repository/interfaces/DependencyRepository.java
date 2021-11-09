package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.JoinColumn;
import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Integer> {

    //List<Dependency> findAllByUserUuid(Iterable<Integer> integers);

    @Query(value = "SELECT * FROM dependency d WHERE d.contact_uuid = ?1",
    nativeQuery = true)
    List<Dependency> returnAllUserUuid(String contactUuid);

    @Query(value = "SELECT * FROM dependency d WHERE d.user_uuid = ?1",
            nativeQuery = true)
    List<Dependency> returnAllContactUuid(String userUuid);

    Boolean existsByUserUuidAndContactUuid(String userUuid, String contactUuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM dependency d WHERE d.contact_uuid = ?1 AND d.user_uuid = ?2",
            nativeQuery = true)
    int deleteContactUuid(String contactUuid, String userUuid);

    Dependency findByUserUuid(String userUuid);
}
