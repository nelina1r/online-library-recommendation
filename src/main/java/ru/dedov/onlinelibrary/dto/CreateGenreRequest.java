package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO запроса на создание жанра
 *
 * @author Alexander Dedov
 * @since 19.10.2024
 */
@Data
@Schema(description = "Запрос на создание жанра")
public class CreateGenreRequest {

	@JsonProperty("genre_name")
	@Schema(description = "Наименование жанра", example = "Поэма")
	@NotBlank(message = "Имя жанра не должно быть пустым")
	private String genreName;
}
