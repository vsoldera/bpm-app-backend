package com.app.springrolejwt.repository.interfaces;


import com.app.springrolejwt.model.Role;
import com.app.springrolejwt.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleEnum name);
}
