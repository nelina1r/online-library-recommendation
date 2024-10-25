package ru.dedov.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ДТО запроса на удаление оценки
 *
 * @author Alexander Dedov
 * @since 21.10.2024
 */
@Data
public class DeleteUserBookRatingRequest {

	@JsonProperty("book_id")
	@NotBlank(message = "id не должно быть пустым")
	private String bookId;
}
