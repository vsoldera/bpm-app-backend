package com.app.springrolejwt.repository.interfaces;


import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findById(Long userId);

	Optional<User> findByPhone(String phone);

	Long findIdByUsername(String username);

	Optional<User> findByRefreshToken(RefreshToken refreshToken);

	Optional<User> findByCode(String code);

	Boolean existsByUsername(String username);

	Boolean existsByPhone(String phone);

	Boolean existsByIsRegistered(Boolean option);

//	@Query("UPDATE user SET username = username WHERE id = id")
//	Optional<User> findQuestionDetails(@Param("username") String username);
}
