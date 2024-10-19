package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * DTO оценки книги пользователем
 *
 * @author Alexander Dedov
 * @since 20.10.2024
 */
@Data
@Schema(description = "Запрос на оценку книги пользователем")
public class UserBookRatingRequest {

	@JsonProperty("book_id")
	private String bookId;
	@Min(value = 1, message = "Оценка должна быть не меньше 1")
	@Max(value = 5, message = "Оценка должна быть не больше 5")
	private Integer rating;
}
