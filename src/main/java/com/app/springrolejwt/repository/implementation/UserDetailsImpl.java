package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Log4j2
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String completeName;

	private String phone;

	@JsonIgnore
	private String password;

	@NotNull
	private String code;

	private Collection<? extends GrantedAuthority> authorities;

	private Date birthDate;
	private Integer weight;
	private Integer height;
	private Boolean sex;
	private Boolean isWheelchairUser;
	private Boolean hasAlzheimer;
	private Boolean isRegistered;

	public UserDetailsImpl(Long id, String username, String completeName, String phone, String password, Collection<? extends GrantedAuthority> authorities, Date birthDate, Integer weight, Integer height, Boolean sex, Boolean isWheelchairUser, Boolean hasAlzheimer) {
		this.id = id;
		this.username = username;
		this.completeName = completeName;
		this.phone = phone;
		this.password = password;
		this.authorities = authorities;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.sex = sex;
		this.isWheelchairUser = isWheelchairUser;
		this.hasAlzheimer = hasAlzheimer;
	}

	public static UserDetailsImpl build(User user) {

		if(user.getIsRegistered()) {
			log.info("There was an error, user is already registered");
		}

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getCompleteName(),
				user.getPhone(),
				user.getPassword(),
				authorities,
				user.getBirthDate(),
				user.getWeight(),
				user.getHeight(),
				user.getSex(),
				user.getIsWheelchairUser(),
				user.getHasAlzheimer()
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
