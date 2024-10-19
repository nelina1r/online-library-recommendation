package ru.dedov.onlinelibrary.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.dedov.onlinelibrary.dto.ErrorResponse;
import ru.dedov.onlinelibrary.service.UserService;
import ru.dedov.onlinelibrary.service.auth.JwtService;

import java.io.IOException;

/**
 * Фильтр аутентификации, который обрабатывает каждый HTTP-запрос и проверяет наличие JWT токена
 * в заголовке Authorization.
 * Если токен присутствует и валиден, пользователь аутентифицируется.
 *
 * Наследник {@link OncePerRequestFilter}, что гарантирует выполнение фильтра один раз за запрос.
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@Component
@RequiredArgsConstructor
@Log
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String HEADER_NAME = "Authorization";
	private final JwtService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		try {
			// Получаем токен из заголовка
			String authHeader = request.getHeader(HEADER_NAME);
			log.info("auth header = " + authHeader);
			if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			// Обрезаем префикс и получаем имя пользователя из токена
			String jwt = authHeader.substring(BEARER_PREFIX.length());
			String username = jwtService.extractUserName(jwt);
			if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userService
					.userDetailsService()
					.loadUserByUsername(username);
				// Если токен валиден, то аутентифицируем пользователя
				if (jwtService.isTokenValid(jwt, userDetails)) {
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
					);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					context.setAuthentication(authToken);
					SecurityContextHolder.setContext(context);
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException ex) {
			// так как исключение возникает на уровне фильтра, не доходя до контроллера, значит RestControllerAdvice
			// работать не будет, и нужно обработать его на месте
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(
				new ObjectMapper().writeValueAsString(
					new ErrorResponse(ex.getLocalizedMessage())
				)
			);
		}
	}
}