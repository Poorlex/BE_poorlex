package com.project.poorlex;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.project.poorlex.api.service.AuthService;
import com.project.poorlex.jwt.JwtTokenProvider;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

	@MockBean
	protected JwtTokenProvider jwtTokenProvider;

	@MockBean
	protected AuthService authService;
}
