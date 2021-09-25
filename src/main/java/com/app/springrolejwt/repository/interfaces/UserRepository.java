package com.app.springrolejwt.repository.interfaces;


import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findById(Long userId);

	Optional<User> findByPhone(String phone);

	Optional<User> findByUuid(String uuid);

	@Query(value = "SELECT * FROM user u WHERE u.uuid = ?1",
			nativeQuery = true)
	List<User> returnAllUserUuid(String uuid);

	Long findIdByUsername(String username);

	Optional<User> findByRefreshToken(RefreshToken refreshToken);

	Optional<User> findByCode(String code);

	Boolean existsByUsername(String username);

	Boolean existsByPhone(String phone);

	Boolean existsByIsRegistered(Boolean option);

	Boolean existsByUuid(String uuid);

//	@Query("UPDATE user SET username = username WHERE id = id")
//	Optional<User> findQuestionDetails(@Param("username") String username);
}
