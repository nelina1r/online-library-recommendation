package ru.dedov.onlinelibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.onlinelibrary.dto.JwtAuthenticationResponse;
import ru.dedov.onlinelibrary.dto.SignInRequest;
import ru.dedov.onlinelibrary.dto.SignUpRequest;
import ru.dedov.onlinelibrary.service.auth.AuthenticationService;

/**
 * Контроллер аутентификации
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
	private final AuthenticationService authenticationService;

	@Operation(summary = "Регистрация пользователя")
	@PostMapping("/sign-up")
	public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
		return authenticationService.signUp(request);
	}

	@Operation(summary = "Авторизация пользователя")
	@PostMapping("/sign-in")
	public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
		return authenticationService.signIn(request);
	}
}