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
	private List<String> roles;
	private String uuid;

	public JwtVo(String accessToken, Long id, String uuid, String username, List<String> roles, String refreshToken) {
		this.token = accessToken;
		this.id = id;
		this.uuid = uuid;
		this.username = username;
		this.roles = roles;
		this.refreshToken = refreshToken;
	}
}
