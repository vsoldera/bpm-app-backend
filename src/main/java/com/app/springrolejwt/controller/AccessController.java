package com.app.springrolejwt.controller;

import com.app.springrolejwt.model.vo.JwtVo;
import com.app.springrolejwt.model.vo.UserDataVo;
import com.app.springrolejwt.repository.implementation.RefreshTokenServiceImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/get")
public class AccessController {

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@GetMapping("/all")
	public String allAccess() {
		return "THIS IS PUBLIIIIIIIIC.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('RESPONSIBLE')")
	public String userAccess() {
		return "Dashboard for elderly or anyone :).";
	}

	@GetMapping("/responsible")
	@PreAuthorize("hasRole('RESPONSIBLE')")
	public String responsibleAccess() {
		return "Responsible has access to this dashboard! ";
	}

	@GetMapping("/data")
	@PreAuthorize("hasRole('USER') or hasRole('RESPONSIBLE')")
	public ResponseEntity<?> userData(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UserDataVo(
				userDetails.getUsername(),
				userDetails.getCompleteName(),
				userDetails.getEmail(),
				roles,
				userDetails.getBirthDate(),
				userDetails.getWeight(),
				userDetails.getHeight(),
				userDetails.getSex(),
				userDetails.getIsWheelchairUser(),
				userDetails.getHasAlzheimer(),
				userDetails.getPhone()

				));

	}
}
