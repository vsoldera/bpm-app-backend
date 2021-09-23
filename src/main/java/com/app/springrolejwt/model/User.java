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
import java.util.UUID;


@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username")
		})
@Data
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String completeName;

	private String uuid;

	private String password;

	private Date birthDate;

	private Integer weight;

	private Integer height;

	//Is this politically correct?
	private Boolean sex;

	private Boolean isWheelchairUser;

	private Boolean hasAlzheimer;

	private String phone;

	private String code;

	private Boolean isRegistered = false;

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

	public User(Long id, String username, String phone, Set<Role> roles,
				Date birthDate, String completeName, Integer weight, Integer height, Boolean sex,
				Boolean isWheelchairUser, Boolean hasAlzheimer) {
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.phone = phone;
		this.birthDate = birthDate;
		this.completeName = completeName;
		this.weight = weight;
		this.height = height;
		this.sex = sex;
		this.isWheelchairUser = isWheelchairUser;
		this.hasAlzheimer = hasAlzheimer;
	}

	public User(String username, String phone, Set<Role> roles,
				Date birthDate, String completeName, Integer weight, Integer height, Boolean sex,
				Boolean isWheelchairUser, Boolean hasAlzheimer) {
		this.username = username;
		this.roles = roles;
		this.phone = phone;
		this.birthDate = birthDate;
		this.completeName = completeName;
		this.weight = weight;
		this.height = height;
		this.sex = sex;
		this.isWheelchairUser = isWheelchairUser;
		this.hasAlzheimer = hasAlzheimer;
	}

	public User(String username, String phone, RefreshToken refreshToken, Boolean isRefreshActive) {
		this.username = username;
		this.phone = phone;
		this.refreshToken = refreshToken;
		this.isRefreshActive = isRefreshActive;
	}

	public User(String code) {
		this.code = code;
	}

}
