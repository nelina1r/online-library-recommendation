package ru.dedov.onlinelibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO ответа с JWT токеном
 *
 * @author Alexander Dedov
 * @since 13.10.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtAuthenticationResponse {

	@Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
	private String token;
}
