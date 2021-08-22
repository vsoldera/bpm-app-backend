package com.app.springrolejwt.model;

import com.app.springrolejwt.model.vo.validation.ValidPhoneNumber;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Data
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

	public User() {
	}

	public User(String username, String email, String phone, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public User(String code) {
		this.code = code;
	}

}
