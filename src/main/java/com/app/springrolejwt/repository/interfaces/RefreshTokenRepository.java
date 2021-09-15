package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    boolean existsByUserId(Long id);

    @Modifying
    int deleteByUserId(Long id);
}
