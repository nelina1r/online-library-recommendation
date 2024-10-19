package ru.dedov.onlinelibrary.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dedov.onlinelibrary.dto.JwtAuthenticationResponse;
import ru.dedov.onlinelibrary.dto.SignInRequest;
import ru.dedov.onlinelibrary.dto.SignUpRequest;
import ru.dedov.onlinelibrary.model.entity.User;
import ru.dedov.onlinelibrary.model.entity.enums.Role;
import ru.dedov.onlinelibrary.service.UserService;

/**
 * Сервис для регистрации/авторизации
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	/**
	 * Регистрация пользователя
	 *
	 * @param request данные пользователя
	 * @return токен
	 */
	public JwtAuthenticationResponse signUp(SignUpRequest request) {
		User user = User.builder()
			.username(request.getUsername())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.ROLE_USER)
			.build();
		userService.createUser(user);
		String jwt = jwtService.generateToken(user);
		return new JwtAuthenticationResponse(jwt);
	}

	/**
	 * Аутентификация пользователя
	 *
	 * @param request данные пользователя
	 * @return токен
	 */
	public JwtAuthenticationResponse signIn(SignInRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			request.getUsername(),
			request.getPassword()
		));
		UserDetails userDetails = userService
			.userDetailsService()
			.loadUserByUsername(request.getUsername());
		String jwt = jwtService.generateToken(userDetails);
		return new JwtAuthenticationResponse(jwt);
	}
}
