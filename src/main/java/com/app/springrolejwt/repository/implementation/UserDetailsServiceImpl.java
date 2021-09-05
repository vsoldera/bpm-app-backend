package com.app.springrolejwt.repository.implementation;


import com.app.springrolejwt.model.PhoneCode;
import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import javassist.NotFoundException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public User findByPhone(String phoneNumber) {
		return userRepository.findByPhone(phoneNumber);
	}

	public User findByCode(String code) {
		return userRepository.findByCode(code);
	}

	public void verifyRefreshAvailability(RefreshToken refreshToken) {
		User userDevice = userRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new RuntimeException("No device found for the matching token. Please login again"));

		if (!userDevice.getIsRefreshActive()) {
			throw new RuntimeException("Refresh blocked for the device. Please login through a different device");		}
	}

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

}
