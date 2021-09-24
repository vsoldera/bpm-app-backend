package com.app.springrolejwt.repository.implementation;


import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);

		return UserDetailsImpl.build(user.get());
	}

	public Optional<User> findByPhone(String phoneNumber) {
		Optional<User> userCode =  userRepository.findByPhone(phoneNumber);

		return userCode;
	}

	public User findByUsername(String username) {
		User user =  userRepository.findByUsername(username).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "O usuário não existe. Tente novamente!"));

		return user;
	}

	public User findByUuid(String uuid) {
		User userUuid =  userRepository.findByUuid(uuid).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "O UUID é inexistente. Por favor, contate o suporte!"));
		return userUuid;
	}

	public User findByCode(String code) {
		User userCode =  userRepository.findByCode(code).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.BAD_REQUEST, "O código é inválido. Tente novamente!"));
		return userCode;
	}

	public String deleteCodeByPhone(String phone) {
		//return userRepository.deleteCodeByPhone(phone);
		return "Whatever";
	}

	public void verifyRefreshAvailability(RefreshToken refreshToken) {
		User userDevice = userRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new RuntimeException("No device found for the matching token. Please login again"));

		if (!userDevice.getIsRefreshActive()) {
			throw new RuntimeException("Refresh blocked for the device. Please login through a different device");		}
	}

	public Optional<User> findByToken(Long userId) {
		return userRepository.findById(userId);
	}

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

}
