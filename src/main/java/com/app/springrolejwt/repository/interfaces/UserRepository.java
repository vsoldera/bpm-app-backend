package com.app.springrolejwt.repository.interfaces;


import com.app.springrolejwt.model.PhoneCode;
import com.app.springrolejwt.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	User findByPhone(String phone);

	User findByCode(String code);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
