package com.app.springrolejwt.security;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Log4j2
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

//		response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		PrintWriter writer = response.getWriter();
//		writer.println("HTTP Status 401 - " + authException.getMessage());

		log.error("Unauthorized error: {}" + authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

//	@Override
//	public void afterPropertiesSet() {
//		setRealmName("DeveloperStack");
//		super.afterPropertiesSet();
//	}

}
