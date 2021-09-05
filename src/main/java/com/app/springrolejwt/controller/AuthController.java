package com.app.springrolejwt.controller;

import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.Role;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.model.enums.RoleEnum;
import com.app.springrolejwt.model.vo.*;
import com.app.springrolejwt.repository.implementation.RefreshTokenServiceImpl;
import com.app.springrolejwt.repository.implementation.SmsServiceImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsServiceImpl;
import com.app.springrolejwt.repository.interfaces.RoleRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import com.app.springrolejwt.security.JwtUtils;
import com.app.springrolejwt.repository.implementation.UserDetailsImpl;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	SmsServiceImpl smsService;

	@Autowired
	RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@PostMapping("/sendSMS")
	public Message sendSMS(@RequestParam String phoneNumber) {
		log.info("There was a POST request to sign in using phone {}" + phoneNumber);
		return smsService.sendSms("+" + phoneNumber);
	}

	@PostMapping("/authCode")
	public ResponseEntity<?> authSMS(@RequestParam String code, @RequestParam String phone) {
		//Optional do find user by phone (userValidation)

		log.info("Code: " + userDetailsService.findByCode(code).getUsername());
		log.info("Password: " + userDetailsService.findByCode(code).getPassword());
		User user = userDetailsService.findByPhone("+" + phone);

		if(user != null) {
			user.setPassword(encoder.encode(user.getCode()));
		}

		assert user != null;
		if(code.equals(user.getCode())) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDetailsService.findByCode(code).getUsername(),
							userDetailsService.findByCode(code).getCode()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());

			log.info("There was a POST request to sign in from user: " + userDetailsService.findByCode(code).getUsername());
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			return ResponseEntity.ok(new JwtVo(jwt,
					userDetails.getId(),
					userDetails.getUsername(),
					userDetails.getEmail(),
					roles, refreshToken.getToken()));

		}

		return ResponseEntity
				.badRequest()
				.body(new MessageVo("Error: Auth failed!"));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginVo loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		log.info("There was a POST request to sign in from user {}" + loginRequest.getUsername());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtVo(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 roles, refreshToken.getToken()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignupVo signUpRequest) {


		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageVo("Error: Username is already taken!"));
		}



		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageVo("Error: Email is already in use!"));
		}

		log.info("Saving user: " + signUpRequest.getUsername() + " with e-mail: "
				+ signUpRequest.getEmail() + " with Phone Number: " + signUpRequest.getPhone() +  " to the database");

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 signUpRequest.getPhone(),
							 encoder.encode(signUpRequest.getPassword()),
									 signUpRequest.getBirthDate(),
									 signUpRequest.getCompleteName(),
									 signUpRequest.getWeight(),
				signUpRequest.getHeight(),
				signUpRequest.getSex(),
				signUpRequest.getIsWheelchairUser(),
				signUpRequest.getHasAlzheimer()
		);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role was not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "responsible":
					Role adminRole = roleRepository.findByName(RoleEnum.ROLE_RESPONSIBLE)
							.orElseThrow(() -> new RuntimeException("Error: Role was not found: " + roleRepository.findByName(RoleEnum.ROLE_RESPONSIBLE)));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role was not found: " + roleRepository.findByName(RoleEnum.ROLE_USER)));
					roles.add(userRole);
				}
			});
		}

		log.info("There was a POST request to sign up from user {}" + signUpRequest.getUsername());

		user.setUuid(UUID.randomUUID().toString().substring(0, 5));

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageVo("User registered successfully!"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {

		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new RuntimeException(
						"Refresh token is not in database!"));
	}

}
