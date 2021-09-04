package com.app.springrolejwt.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class JwtVo {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtVo(String accessToken, Long id, String username, String email, List<String> roles, String refreshToken) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.refreshToken = refreshToken;
	}
}
