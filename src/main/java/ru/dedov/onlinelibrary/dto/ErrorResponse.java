package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO, которое передает текст ошибки в случае ее возникновения
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Data
@Schema(description = "Ответ с текстом ошибки")
@AllArgsConstructor
public class ErrorResponse {

	@JsonProperty("error_message")
	@Schema(description = "Текст ошибки", example = "Пользователь с таким email уже существует")
	private String errorMessage;
}