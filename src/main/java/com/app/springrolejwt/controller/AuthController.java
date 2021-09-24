package com.app.springrolejwt.controller;

import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.RefreshToken;
import com.app.springrolejwt.model.Role;
import com.app.springrolejwt.model.vo.tokenVos.TokenRefreshRequest;
import com.app.springrolejwt.model.vo.tokenVos.TokenRefreshResponse;
import com.app.springrolejwt.model.vo.userVos.UserSignupVo;
import com.app.springrolejwt.repository.implementation.*;
import com.app.springrolejwt.repository.interfaces.HealthRepository;
import org.springframework.security.core.Authentication;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.model.enums.RoleEnum;
import com.app.springrolejwt.model.vo.*;
import com.app.springrolejwt.repository.interfaces.RefreshTokenRepository;
import com.app.springrolejwt.repository.interfaces.RoleRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import com.app.springrolejwt.security.JwtUtils;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	HealthServiceImpl healthService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	HealthRepository healthRepository;

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
	@Transactional
	public Message sendSMS(@RequestParam String phoneNumber) {
		log.info("There was a POST request to sign in using phone " + phoneNumber);
		return smsService.sendSms("+" + phoneNumber);
	}

	@PostMapping("/authCode")
	@Transactional
	public ResponseEntity<?> authSMS(@RequestParam String code, @RequestParam String phone) {
		//Optional do find user by phone (userValidation)

		if(code == null || phone == null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageVo("Por favor, digite um código válido!"));
		}

		Optional<User> user = userRepository.findByUsername("+" + phone);

		if (code.equals(userDetailsService.findByCode(code).getCode()) && code.equals(userDetailsService.findByUsername("+" + phone).getCode()) && code != null) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDetailsService.findByCode(code).getUsername(),
							userDetailsService.findByCode(code).getCode()));

			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//			if (userRepository.existsByIsRegistered(true)) {
//				user.get().setCode(null);
//				userRepository.save(user.get());
//			}

			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());

			if (refreshTokenRepository.existsByUserId(userDetails.getId())) {
				refreshTokenRepository.deleteByUserId(userDetails.getId());
			}

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
			userDetailsService.deleteCodeByPhone("+" + phone);

			return ResponseEntity.ok(new JwtVo(jwt,
					userDetails.getId(),
					userDetails.getUuid(),
					userDetails.getUsername(),
					roles, refreshToken.getToken()
					));

		}

		return ResponseEntity
				.badRequest()
				.body(new MessageVo("Por favor, digite um código válido!"));
	}


//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginVo loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		log.info("There was a POST request to sign in from user {}" + loginRequest.getUsername());
//		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//		return ResponseEntity.ok(new JwtVo(jwt,
//												 userDetails.getId(),
//												 userDetails.getUsername(),
//												 roles, refreshToken.getToken()));
//	}

	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<?> registerUser(@RequestBody UserSignupVo signUpRequest) throws ParseException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
		Optional<User> username = userRepository.findByUsername(auth.getName());

		if (userRepository.existsByUsername((username.get().getUsername()))) {
			log.info("Updating infos from user: " + username.get().getUsername()
					+  " to the database" + "Encrypting code: " + username.get().getCode());


			if(username.get().getIsRegistered()) {
				username.get().setPhone(username.get().getUsername());
				username.get().setBirthDate(signUpRequest.getBirthDate());
				username.get().setCompleteName(signUpRequest.getCompleteName());
				username.get().setWeight(signUpRequest.getWeight());
				username.get().setHeight(signUpRequest.getHeight());
				username.get().setSex(signUpRequest.getSex());
				username.get().setIsWheelchairUser(signUpRequest.getIsWheelchairUser());
				username.get().setHasAlzheimer(signUpRequest.getHasAlzheimer());
			} else {
				//dar uma olhada aqui
				//username.get().setPassword(encoder.encode(username.get().getCode()));
				username.get().setPhone(username.get().getUsername());
				username.get().setBirthDate(signUpRequest.getBirthDate());
				username.get().setCompleteName(signUpRequest.getCompleteName());
				username.get().setWeight(signUpRequest.getWeight());
				username.get().setHeight(signUpRequest.getHeight());
				username.get().setSex(signUpRequest.getSex());
				username.get().setIsWheelchairUser(signUpRequest.getIsWheelchairUser());
				username.get().setHasAlzheimer(signUpRequest.getHasAlzheimer());
				username.get().setIsRegistered(true);
				username.get().setPassword(encoder.encode(username.get().getCode()));
			}

			Set<String> strRoles = signUpRequest.getRole();
			log.info("Role: " + strRoles);
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

			log.info("There was a POST request to sign up from user {}" + username.get().getUsername());

			username.get().setRoles(roles);

			userRepository.save(username.get());

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username.get().getUsername(), username.get().getCode()));


			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> role = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());

			log.info("There was a POST request to sign in from user " + username.get().getUsername());

			if(refreshTokenRepository.existsByUserId(username.get().getId())) {
				refreshTokenRepository.deleteByUserId(username.get().getId());
			}

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			return ResponseEntity.ok(new JwtVo(jwt,
					userDetails.getId(),
					userDetails.getUuid(),
					userDetails.getUsername(),
					role, refreshToken.getToken()));
		}

		return ResponseEntity
				.badRequest()
				.body(new MessageVo("Error: Username is already taken!"));
	}

	@PostMapping("/refreshtoken")
	@Transactional
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
