package com.app.springrolejwt.model;

import com.app.springrolejwt.model.vo.validation.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email"),
				@UniqueConstraint(columnNames = "phone")
		})
@Data
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@NotNull
	private String username;

	@NotBlank
	@NotNull
	@Email
	private String email;

	@NotBlank
	@NotNull
	private String password;

	@NotBlank
	@NotNull
	private Date birthDate;

	@NotBlank
	@NotNull
	private Integer weight;

	@NotBlank
	@NotNull
	private Integer height;

	@NotBlank
	@NotNull
	//Is this politically correct?
	private Boolean sex;

	@NotBlank
	@NotNull
	private Boolean isWheelchairUser;

	@NotBlank
	@NotNull
	private Boolean hasAlzheimer;

	@NotNull
	@NotBlank
	private String phone;

	@NotNull
	private String code;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToOne(mappedBy = "user")
	private RefreshToken refreshToken;
	private Boolean isRefreshActive;

	public User() {
	}

	public User(String username, String email, String phone, String password,
				Date birthDate, Integer weight, Integer height, Boolean sex,
				Boolean isWheelchairUser, Boolean hasAlzheimer) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.sex = sex;
		this.isWheelchairUser = isWheelchairUser;
		this.hasAlzheimer = hasAlzheimer;
	}

	public User(String username, String email, String phone, String password, RefreshToken refreshToken, Boolean isRefreshActive) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.refreshToken = refreshToken;
		this.isRefreshActive = isRefreshActive;
	}

	public User(String code) {
		this.code = code;
	}

}
